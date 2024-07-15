package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viettridao.entity.TransactionHistory;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, String> {
}
