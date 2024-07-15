package com.viettridao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettridao.dto.TransactionHistoryCreationRequest;
import com.viettridao.dto.TransactionHistoryUpdateRequest;
import com.viettridao.entity.AuctionDetail;
import com.viettridao.entity.TransactionHistory;
import com.viettridao.entity.User;
import com.viettridao.repository.TransactionHistoryRepository;
import com.viettridao.repository.UserRepository;
import com.viettridao.service.iTransactionHistoryService;
import com.viettridao.repository.auctionDetailRepository ;

@Service
public class TransactionHistoryService implements iTransactionHistoryService{
    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private auctionDetailRepository auctionDetailRepository;
    @Override
    public TransactionHistory createRequest(TransactionHistoryCreationRequest request) {
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTransactionHistoryId(request.getTransactionHistoryId());
        transactionHistory.setNote(request.getNote());
        transactionHistory.setStatus(request.getStatus());
        transactionHistory.setDepositCost(request.getDepositCost());
        transactionHistory.setTradeCost(request.getTradeCost());
        transactionHistory.setTradeDate(request.getTradeDate());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        transactionHistory.setUser(user);

        AuctionDetail auctionDetail = auctionDetailRepository.findById(request.getAuctionDetailId())
                .orElseThrow(() -> new RuntimeException("AuctionDetail not found"));
        transactionHistory.setAuctionDetail(auctionDetail);

        return transactionHistoryRepository.save(transactionHistory);
    }
   
 
    @Override
    public TransactionHistory UpdateRequest(String transactionHistoryId, TransactionHistoryUpdateRequest request) {
        TransactionHistory transactionHistory = getTransactionHistory(transactionHistoryId);
        transactionHistory.setNote(request.getNote());
        transactionHistory.setStatus(request.getStatus());
        transactionHistory.setDepositCost(request.getDepositCost());
        transactionHistory.setTradeCost(request.getTradeCost());
        transactionHistory.setTradeDate(request.getTradeDate());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        transactionHistory.setUser(user);

        AuctionDetail auctionDetail = auctionDetailRepository.findById(request.getAuctionDetailId())
                .orElseThrow(() -> new RuntimeException("AuctionDetail not found"));
        transactionHistory.setAuctionDetail(auctionDetail);

        return transactionHistoryRepository.save(transactionHistory);
    }
	@Override
    public List<TransactionHistory> getTransactionHistorys()
    {
        return transactionHistoryRepository.findAll();
    }
	@Override
    public TransactionHistory getTransactionHistory(String TransactionHistoryId){
        return transactionHistoryRepository.findById(TransactionHistoryId).orElseThrow(()-> new RuntimeException("Tax not found"));
    }
	@Override
    public void deleteTransactionHistory(String TransactionHistoryId){
        transactionHistoryRepository.deleteById(TransactionHistoryId);
    }
    
}