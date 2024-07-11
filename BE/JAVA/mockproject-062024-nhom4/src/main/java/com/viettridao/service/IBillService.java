package com.viettridao.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.viettridao.dto.BillDTO;
import com.viettridao.entity.Bill;

public interface IBillService {
	public BillDTO saveOrUpdate(BillDTO request);
	
	public Bill delete(String billId);
	
	public Bill findById(String billId);
	
	public List<BillDTO> getAllBills(Pageable pageable);
	
	public List<BillDTO> getBillsByUserId(String userId, Pageable pageable);
	
	public BillDTO getBillById(String billId);
	
	public int getTotalItem();
}
