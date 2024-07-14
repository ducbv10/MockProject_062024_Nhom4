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

import com.viettridao.dto.TransactionHistoryCreationRequest;
import com.viettridao.dto.TransactionHistoryUpdateRequest;
import com.viettridao.entity.TransactionHistory;
import com.viettridao.service.impl.TransactionHistoryService;

@RestController
@RequestMapping("/transactionhistorys")
public class TransactionHistoryController {
    @Autowired
    
    private TransactionHistoryService transactionHistoryService;

    @PostMapping
    TransactionHistory createTransactionHistory(@RequestBody TransactionHistoryCreationRequest request) {

        return transactionHistoryService.createRequest(request);
    }

    @GetMapping
    List<TransactionHistory> getTransactionHistory() {
        return transactionHistoryService.getTransactionHistorys();
    }

    @GetMapping("/{TransactionHistoryId}")
    TransactionHistory getTransactionHistorys(@PathVariable("TransactionHistoryId") String TransactionHistoryId) {
        return transactionHistoryService.getTransactionHistory(TransactionHistoryId);
    }

    @PutMapping("/{TransactionHistoryId}")
    TransactionHistory updateTransactionHistory(@PathVariable("TransactionHistoryId") String TransactionHistoryId, @RequestBody TransactionHistoryUpdateRequest request) {
        return transactionHistoryService.UpdateRequest(TransactionHistoryId,request);
    }

    @DeleteMapping("/{TransactionHistoryId}")
    String deleteTransactionHistory(@PathVariable("TransactionHistoryId") String TransactionHistoryId) {
        transactionHistoryService.deleteTransactionHistory(TransactionHistoryId);
        return "user has been delete";
    }
}
