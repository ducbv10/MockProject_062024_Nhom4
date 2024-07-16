package com.viettridao.service.impl;

import java.time.LocalDateTime;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.dto.AuctionDetailDTO;
import com.viettridao.entity.Asset;
import com.viettridao.entity.Auction;
import com.viettridao.entity.AuctionDetail;
import com.viettridao.entity.User;
import com.viettridao.exception.NotFoundException;
import com.viettridao.repository.AssetRepository;
import com.viettridao.repository.AuctionRepository;
import com.viettridao.repository.UserRepository;
import com.viettridao.repository.auctionDetailRepository;
import com.viettridao.request.AuctionDetailRequest;
import com.viettridao.service.IAuctionDetailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AuctionDetailServiceImpl implements IAuctionDetailService {

	@Autowired
	private auctionDetailRepository auctionDetailRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private AssetRepository assetRepo;
	
	@Autowired
	private AuctionRepository auctionRepo;
	
	@Autowired
	private ModelMapper mapper;

	private String generateCustomId() {
		long count = auctionDetailRepo.count();

		count = count + 1;

		String countStr = String.valueOf(count);

		String prefix = "ACD" + "0".repeat(Math.max(0, 7 - countStr.length()));

		return prefix + countStr;
	}
	
	private AuctionDetail convertRequestToEntity(AuctionDetail auctionDetail, AuctionDetailRequest request) {
		try {
			if (request.getAuctionId() != null) {
				Auction auction = auctionRepo.findById(request.getAuctionId())
						.orElseThrow(() -> new NotFoundException("Not found auction id " + request.getAuctionId()));
				auctionDetail.setAuction(auction);;
			}
			
			if (request.getWonId() != null && !request.getWonId().equals(auctionDetail.getWonUser().getUserId())) {
				User winner =  userRepo.findById(request.getWonId())
						.orElseThrow(() -> new NotFoundException("Not found winner id " + request.getWonId()));;
				auctionDetail.setWonUser(winner);;
			}
			
			if (request.getHostId() != null && !request.getHostId().equals(auctionDetail.getHostUser().getUserId())) {
				User host = userRepo.findById(request.getHostId())
						.orElseThrow(() -> new NotFoundException("Not found host id " + request.getHostId()));;
				auctionDetail.setHostUser(host);;
			}
			
			if (request.getAssetId() != null && !request.getAssetId().equals(auctionDetail.getAsset().getAssetId())) {
				Asset asset = assetRepo.findById(request.getAssetId())
						.orElseThrow(() -> new NotFoundException("Not found asset id " + request.getAssetId()));;
				auctionDetail.setAsset(asset);
			}
			
			auctionDetail.setPercentPrice(request.getPercentPrice());
			auctionDetail.setPresentPrice(request.getPresentPrice());
			auctionDetail.setStartingPrice(request.getStartingPrice());
			auctionDetail.setStep(request.getStep());
			auctionDetail.setTotalTime(request.getTotaltime());
			
			return auctionDetail;
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public void createNewAuctionDetail(AuctionDetailRequest request) {
		try {
			log.info("Create new auction detail.");

			if (request.getAuctionDetailId() == null) {
				AuctionDetail auctionDetail = new AuctionDetail();
				auctionDetail.setAuctionDetailId(generateCustomId());
				convertRequestToEntity(auctionDetail, request);
				auctionDetailRepo.save(auctionDetail);
			}
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public void updateAuctionDetail(AuctionDetailRequest request) {
		try {
			log.info("Update info auction detail id {}", request.getAuctionDetailId());
			AuctionDetail oldAuctionDetail = auctionDetailRepo.findById(request.getAuctionDetailId())
					.orElseThrow(() -> new NotFoundException("Not found auction detail id " + request.getAuctionDetailId()));
			convertRequestToEntity(oldAuctionDetail, request);
			auctionDetailRepo.save(oldAuctionDetail);
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public void deleteAuctionDetailById(String auctionDetailId) {
		try {
			AuctionDetail auctionDetail = auctionDetailRepo.findById(auctionDetailId)
					.orElseThrow(() -> new RuntimeException("Not found auction detail id " + auctionDetailId));
			
			auctionDetail.setDeleteAt(LocalDateTime.now());
			auctionDetailRepo.save(auctionDetail);
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public AuctionDetailDTO findAuctionDetailById(String auctionDetailId) {
		try {
			AuctionDetail auctionDetail = auctionDetailRepo.findById(auctionDetailId)
					.orElseThrow(() -> new RuntimeException("Not found auction detail id " + auctionDetailId));
			return mapper.map(auctionDetail, AuctionDetailDTO.class);
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}
}
