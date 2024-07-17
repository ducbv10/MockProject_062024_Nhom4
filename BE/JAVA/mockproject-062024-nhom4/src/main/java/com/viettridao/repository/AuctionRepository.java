package com.viettridao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Auction;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, String> {

	@Query(value = "SELECT a FROM Auction a WHERE a.auctionId = :auctionId AND a.deleteAt IS null")
	Optional<Auction> findById(@Param("auctionId") String auctionId);

	@Query("SELECT a FROM Auction a WHERE "
            + "(:method IS NULL OR :method = '' OR a.method = :method) AND "
            + "(:isSecret IS NULL OR :isSecret = '' OR a.isSecret = :isSecret) AND "
            + "(:status IS NULL OR :status = '' OR a.status = :status) AND a.deleteAt IS NULL")
    List<Auction> findAuctionsByMethodAndSecretAndStatus(@Param("method") AuctionMethod method, 
                               @Param("isSecret") AuctionIsSecret isSecret, 
                               @Param("status") AuctionStatus status);
}
