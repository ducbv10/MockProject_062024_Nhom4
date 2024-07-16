package com.viettridao.service;

import com.viettridao.dto.AuctionDetailDTO;
import com.viettridao.request.AuctionDetailRequest;

public interface IAuctionDetailService {

	void createNewAuctionDetail(AuctionDetailRequest request);
	
	void updateAuctionDetail(AuctionDetailRequest request);
	
	void deleteAuctionDetailById(String auctionDetailId);
	
	AuctionDetailDTO findAuctionDetailById(String auctionDetailId);
}
