package com.viettridao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.TransactionHistory;
import com.viettridao.entity.AuctionDetail;

@Repository
public interface auctionDetailRepository extends JpaRepository<AuctionDetail, String> {
	Optional<AuctionDetail> findById(String id);
}
