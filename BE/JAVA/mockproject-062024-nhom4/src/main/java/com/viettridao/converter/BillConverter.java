package com.viettridao.converter;

import org.springframework.stereotype.Component;

import com.viettridao.dto.BillDTO;
import com.viettridao.entity.Bill;

@Component
public class BillConverter {
	public Bill toEntity(BillDTO dto) {
		Bill entity = new Bill();
		entity.setBillId(dto.getBillId());
		entity.setTotalAmount(dto.getTotalAmount());
		entity.setStatus(dto.getStatus());
		entity.setPaymentTime(dto.getPaymentTime());
		
		return entity;
	}
	
	public BillDTO toDTO(Bill entity) {
		BillDTO dto = new BillDTO();
		
		dto.setBillId(entity.getBillId());
		dto.setAssetId(entity.getAsset().getAssetId());
		dto.setAssetName(entity.getAsset().getName());
		dto.setUserId(entity.getUser().getUserId());
		dto.setUserName(entity.getUser().getName());
		dto.setTotalAmount(entity.getTotalAmount());
		dto.setStatus(entity.getStatus());
		dto.setPaymentTime(entity.getPaymentTime());
		dto.setTaxId(entity.getTax().getTaxId());
		dto.setTaxName(entity.getTax().getTypeTax());
		dto.setTaxValue(entity.getTax().getValue());
		
		return dto;
	}
	
	public Bill toEntity(BillDTO dto, Bill oldEntity) {
		Bill entity = oldEntity;
		
		entity.setTotalAmount(dto.getTotalAmount());
		entity.setStatus(dto.getStatus());
		entity.setPaymentTime(dto.getPaymentTime());
		
		return entity;
	}
}
