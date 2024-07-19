package com.viettridao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Asset;
import com.viettridao.entity.AuctionDetail;
import com.viettridao.entity.User;

@Repository
public interface auctionDetailRepository extends JpaRepository<AuctionDetail, String> {
	Optional<AuctionDetail> findById(String id);
	
//	@Query("SELECT a, au.startDate FROM AuctionDetail acd "
//			+ "JOIN Auction au ON au.auctionId = acd.auction.auctionId "
//			+ "JOIN Asset a ON a.assetId = a.asset.assetId "
//			+ "WHERE acd.wonUser = :wonId")
//	List<Asset> findByWonId(User wonId);
}
