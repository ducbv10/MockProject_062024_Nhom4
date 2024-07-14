package com.viettridao.service;

import java.util.List;

import com.viettridao.dto.TaxCreationRequest;
import com.viettridao.dto.TaxUpdateRequest;
import com.viettridao.entity.Tax;

public interface iTaxService {

	 public Tax createRequeste(TaxCreationRequest request);
	    public Tax UpdateRequeste(String TaxId, TaxUpdateRequest request);
	    public List<Tax> getTaxs();
	    public Tax getTax(String taxId);
	    public void deleteTax(String taxId);
}
