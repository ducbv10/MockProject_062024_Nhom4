package com.viettridao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Asset;
import com.viettridao.entity.User;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
	Optional<Asset> findById(String id);
	
	@Query("SELECT a FROM Asset a "
			+ "JOIN AuctionDetail acd ON a.assetId = acd.asset.assetId "
			+ "WHERE acd.wonUser = :wonId")
    List<Asset> findByWonId(@Param("wonId") User wonId);
}
