package com.viettridao.service;

import java.util.List;

import com.viettridao.dto.TransactionHistoryCreationRequest;
import com.viettridao.dto.TransactionHistoryUpdateRequest;
import com.viettridao.entity.TransactionHistory;

public interface iTransactionHistoryService {

	 public TransactionHistory createRequest(TransactionHistoryCreationRequest request);
	 public TransactionHistory UpdateRequest(String TransactionHistoryId, TransactionHistoryUpdateRequest request);
	 public List<TransactionHistory> getTransactionHistorys();
	 public TransactionHistory getTransactionHistory(String TransactionHistoryId);
	 public void deleteTransactionHistory(String TransactionHistoryId);
}
