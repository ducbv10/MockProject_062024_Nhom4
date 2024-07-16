package com.viettridao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettridao.request.AuctionDetailRequest;
import com.viettridao.response.ResponseData;
import com.viettridao.response.ResponseError;
import com.viettridao.service.impl.AuctionDetailServiceImpl;

@RestController
@RequestMapping("/auction-detail")
public class AuctionDetailController {

	@Autowired
	private AuctionDetailServiceImpl auctionDetailService;

	@GetMapping("/{auctionDetailId}")
	public ResponseData<?> findAuctionDetailById(@PathVariable("auctionDetailId") String auctionDetailId) {
		try {
			return new ResponseData<>(HttpStatus.OK.value(), "Find auction detail", 
					auctionDetailService.findAuctionDetailById(auctionDetailId));
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}
	
	@PostMapping
	public ResponseData<?> createNewAuctionDetail(@RequestBody AuctionDetailRequest request) {
		try {
			auctionDetailService.createNewAuctionDetail(request);;
			return new ResponseData<>(HttpStatus.CREATED.value(), "New auction detail");
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}
	
	@PutMapping
	public ResponseData<?> updateAuctionDetail(@RequestBody AuctionDetailRequest request) {
		try {
			auctionDetailService.updateAuctionDetail(request);
			return new ResponseData<>(HttpStatus.OK.value(), "Update auction detail");
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}
	
	@DeleteMapping("/{auctionDetailId}")
	public ResponseData<?> deleteAuctionDetail(@PathVariable("auctionDetailId") String auctionDetailId) {
		try {
			auctionDetailService.deleteAuctionDetailById(auctionDetailId);
			return new ResponseData<>(HttpStatus.OK.value(), "Delete auction detail");
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}
}
