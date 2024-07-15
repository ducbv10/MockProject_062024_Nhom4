package com.viettridao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettridao.dto.TaxCreationRequest;
import com.viettridao.dto.TaxUpdateRequest;
import com.viettridao.entity.Tax;
import com.viettridao.service.impl.TaxService;

@RestController
@RequestMapping ("/taxs")
public class TaxController {
    @Autowired
    
    private TaxService taxService;

    @PostMapping
    Tax createTax(@RequestBody TaxCreationRequest request) {

        return taxService.createRequeste(request);
    }

    @GetMapping
    List<Tax> getTaxes() {
        return taxService.getTaxs();
    }

    @GetMapping("/{taxId}")
    Tax getTaxe(@PathVariable("taxId") String taxId) {
        return taxService.getTax(taxId);
    }

    @PutMapping("/{taxId}")
    Tax updateTax(@PathVariable("taxId") String taxId, @RequestBody TaxUpdateRequest request) {
        return taxService.UpdateRequeste(taxId,request);
    }

    @DeleteMapping("/{taxId}")
    String deleteTax(@PathVariable("taxId") String taxId) {
        taxService.deleteTax(taxId);
        return "user has been delete";
    }
}

