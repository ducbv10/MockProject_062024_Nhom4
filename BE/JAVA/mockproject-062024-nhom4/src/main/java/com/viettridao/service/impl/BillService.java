package com.viettridao.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.viettridao.converter.BillConverter;
import com.viettridao.dto.BillDTO;
import com.viettridao.entity.Asset;
import com.viettridao.entity.Bill;
import com.viettridao.entity.Tax;
import com.viettridao.entity.User;
import com.viettridao.exception.AppException;
import com.viettridao.exception.ErrorCode;
import com.viettridao.repository.AssetRepository;
import com.viettridao.repository.BillRepository;
import com.viettridao.repository.TaxRepository;
import com.viettridao.repository.UserRepository;
import com.viettridao.service.IBillService;

@Service
public class BillService implements IBillService {
	@Autowired
	private BillRepository billRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaxRepository taxRepository;
	
	@Autowired
	private AssetRepository assetRepository;
	
	@Autowired
	private BillConverter billConverter;
	
	// save or update Bill entity -> if billId existed ? update Bill : insert Bill to database
	@Override
	public BillDTO saveOrUpdate(BillDTO request) {	
		Bill bill = new Bill();
		if (billRepository.existsById(request.getBillId())) {
			Bill oldBill = billRepository.findById(request.getBillId()).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_EXISTED));
			bill = billConverter.toEntity(request, oldBill);
		} else {
			bill = billConverter.toEntity(request);
		}
		User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		Tax tax = taxRepository.findById(request.getTaxId()).orElseThrow(() -> new AppException(ErrorCode.TAX_NOT_EXISTED));
		Asset asset = assetRepository.findById(request.getAssetId()).orElseThrow(() -> new AppException(ErrorCode.ASSET_NOT_EXISTED));
		
		bill.setUser(user);
		bill.setTax(tax);
		bill.setAsset(asset);
		
		bill = billRepository.save(bill);		
		return billConverter.toDTO(bill);
	}

	@Override
	public Bill delete(String billId) {
		if (!billRepository.existsById(billId)) {
			throw new AppException(ErrorCode.BILL_NOT_EXISTED);
		}
		
		Bill bill = billRepository.findById(billId).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_EXISTED));
		// not delete permanently but change column DeleteAt
		bill.setDeleteAt(LocalDateTime.now());
		return billRepository.save(bill);
	}

	@Override
	public List<BillDTO> getAllBills(Pageable pageable) {
		List<Bill> bills = billRepository.findAllByDeleteAt(null, pageable);
		
		List<BillDTO> results = new ArrayList<>();
		for (Bill bill : bills) {
			results.add(billConverter.toDTO(bill));
		}
		return results;
	}

	@Override
	public List<BillDTO> getBillsByUserId(String userId, Pageable pageable) {
		User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
		List<Bill> bills = billRepository.findAllByUser(user, pageable);
		
		List<BillDTO> results = new ArrayList<>();
		for (Bill bill : bills) {
			// take a list bill is not deleted
			if (bill.getDeleteAt() == null) {
				results.add(billConverter.toDTO(bill));
			}
		}
		return results;
	}

	@Override
	public BillDTO getBillById(String billId) {
		Bill bill = billRepository.findById(billId)
				.orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_EXISTED));
		if (bill.getDeleteAt() != null) {
			throw new AppException(ErrorCode.BILL_NOT_EXISTED);
		}
		return billConverter.toDTO(bill);
	}
	
	@Override
	public Bill findById(String billId) {
		return billRepository.findById(billId).orElseThrow(() -> new AppException(ErrorCode.BILL_NOT_EXISTED));
	}

	@Override
	public int getTotalItem() {
		return (int) billRepository.count();
	}

}
