package com.viettridao.service;

import java.util.List;

import com.viettridao.dto.AuctionDTO;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;

public interface IAuctionService {

	AuctionDTO createNewAuction(AuctionDTO auctionDTO);

	AuctionDTO updateAuction(AuctionDTO auctionDTO);

	AuctionDTO findAuctionById(String auctionId);

	List<AuctionDTO> findAllAuction();
	
	List<AuctionDTO> findAllAuctionPageable(int pageNo, int pageSize);
	
	List<AuctionDTO> findByMethodAndIsSecretAndStatus(AuctionMethod method, AuctionIsSecret isSecret, AuctionStatus status);

	String deleteAuctionById(String auctionId);
}
