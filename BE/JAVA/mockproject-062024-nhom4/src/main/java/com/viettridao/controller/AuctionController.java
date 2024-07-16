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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.viettridao.dto.AuctionDTO;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;
import com.viettridao.response.ResponseData;
import com.viettridao.response.ResponseError;
import com.viettridao.service.impl.AuctionServiceImpl;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

	@Autowired
	private AuctionServiceImpl auctionService;

	@GetMapping("/all")
	public ResponseData<?> getAllAuctions() {
		try {
			return new ResponseData<>(HttpStatus.OK.value(), "List auctions", auctionService.findAllAuction());
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}

	@PostMapping
	public ResponseData<?> createNewAuction(@RequestBody AuctionDTO auction) {
		try {
			return new ResponseData<>(HttpStatus.CREATED.value(), "Create new auction name " + auction.getName(),
					auctionService.createNewAuction(auction));
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}

	@PutMapping
	public ResponseData<?> updateAuction(@RequestBody AuctionDTO auction) {
		try {
			auctionService.createNewAuction(auction);
			return new ResponseData<>(HttpStatus.OK.value(), "Update auction name " + auction.getName());
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}

	@GetMapping("/{auctionId}")
	public ResponseData<?> findAuctionById(@PathVariable("auctionId") String auctionId) {
		try {
			return new ResponseData<>(HttpStatus.OK.value(), "Find auction id " + auctionId,
					auctionService.findAuctionById(auctionId));
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}

	@GetMapping
	public ResponseData<?> findAuctionByStatusAndSecretAndMethod(
			@RequestParam(required = true, defaultValue = "") AuctionMethod method,
			@RequestParam(required = true, defaultValue = "") AuctionIsSecret isSecret,
			@RequestParam(required = true, defaultValue = "") AuctionStatus status) {
		try {
			return new ResponseData<>(HttpStatus.OK.value(), "Find auctions by status and secret and method",
					auctionService.findByMethodAndIsSecretAndStatus(method, isSecret, status));
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}

	@DeleteMapping("/{auctionId}")
	public ResponseData<?> deleteAuctionById(@PathVariable("auctionId") String auctionId) {
		try {
			auctionService.deleteAuctionById(auctionId);
			return new ResponseData<>(HttpStatus.OK.value(), "Delete auction id " + auctionId);
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}

//	@GetMapping
//	public ResponseData<?> findAllAuctionWithPageable(@RequestParam("pageNo") int pageNo,
//			@RequestParam("limit") int pageSize) {
//		try {
//			return new ResponseData<>(HttpStatus.OK.value(), "Find list auction page " + pageNo,
//					auctionService.findAllAuctionPageable(pageNo, pageSize));
//		} catch (Exception e) {
//			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
//		}
//	}
}
