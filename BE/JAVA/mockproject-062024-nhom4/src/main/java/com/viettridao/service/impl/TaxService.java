package com.viettridao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettridao.dto.TaxCreationRequest;
import com.viettridao.dto.TaxUpdateRequest;
import com.viettridao.entity.Tax;
import com.viettridao.entity.User;
import com.viettridao.repository.TaxRepository;
import com.viettridao.repository.UserRepository;
import com.viettridao.service.iTaxService;

@Service
public class TaxService implements iTaxService{
    @Autowired
    private TaxRepository taxRepository;

    @Autowired UserRepository userRepository;
    @Override
    public Tax createRequeste(TaxCreationRequest request)
    {
    	Tax tax = new Tax();
        tax.setTaxId(request.getTaxId());
        tax.setTypeTax(request.getTypeTax());
        tax.setLocation(request.getLocation());
        tax.setValue(request.getValue());
        tax.setDeleteAt(request.getDeleteAt());
//        User user = userRepository.findById(request.getUserId())
//                .orElseThrow(() -> new RuntimeException("User not found"));
//        tax.setUser(user);

        return taxRepository.save(tax);
    }
    @Override
    public Tax UpdateRequeste(String TaxId, TaxUpdateRequest request){
    	 Tax tax = getTax(TaxId);
         tax.setTypeTax(request.getTypeTax());
         tax.setLocation(request.getLocation());
         tax.setValue(request.getValue());
         tax.setDeleteAt(request.getDeleteAt());
         User user = userRepository.findById(request.getUserId())
                 .orElseThrow(() -> new RuntimeException("User not found"));
         tax.setUser(user);

         return taxRepository.save(tax);
    }
    @Override
    public List<Tax> getTaxs()
    {
        return taxRepository.findAll();
    }
    @Override
    public Tax getTax(String taxId){
        return taxRepository.findById(taxId).orElseThrow(()-> new RuntimeException("Tax not found"));
    }
    @Override
    public void deleteTax(String taxId){
        taxRepository.deleteById(taxId);
    }
}
