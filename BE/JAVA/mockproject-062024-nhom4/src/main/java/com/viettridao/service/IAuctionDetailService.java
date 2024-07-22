package com.viettridao.service;

import com.viettridao.dto.AuctionDetailDTO;
import com.viettridao.request.AuctionDetailRequest;

public interface IAuctionDetailService {

	AuctionDetailDTO createNewAuctionDetail(AuctionDetailRequest request);
	
	AuctionDetailDTO updateAuctionDetail(AuctionDetailRequest request);
	
	String deleteAuctionDetailById(String auctionDetailId);
	
	AuctionDetailDTO findAuctionDetailById(String auctionDetailId);
	
	String updateHostUser(String hostUser, String auctionDetailId);
	
	String updateWinner(String winnerId, String auctionDetailId);
}
