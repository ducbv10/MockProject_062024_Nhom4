package com.viettridao.service.impl;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viettridao.dto.AuctionDTO;
import com.viettridao.entity.Auction;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;
import com.viettridao.exception.AuctionException;
import com.viettridao.exception.HolidayException;
import com.viettridao.repository.AuctionRepository;
import com.viettridao.repository.HolidayRepository;
import com.viettridao.service.IAuctionService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class AuctionServiceImpl implements IAuctionService {

	@Autowired
	private AuctionRepository auctionRepo;

	@Autowired
	private HolidayRepository holidayRepo;

	@Autowired
	private ModelMapper mapper;

	private String generateCustomId() {
		long count = auctionRepo.count();

		count = count + 1;

		String countStr = String.valueOf(count);

		String prefix = "AC" + "0".repeat(Math.max(0, 8 - countStr.length()));

		return prefix + countStr;
	}

	private void checkHoliday(LocalDateTime start, LocalDateTime end) {
		try {
			String startDate = holidayRepo.findBetweenHolidayStartAndHolidayEnd(start);

			if (startDate != null)
				throw new HolidayException(start + " is " + startDate);

			String endDate = holidayRepo.findBetweenHolidayStartAndHolidayEnd(end);

			if (endDate != null)
				throw new HolidayException(end + " is " + endDate);
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public AuctionDTO createNewAuction(AuctionDTO auctionDTO) {
		try {
			log.info("Create new auction name " + auctionDTO.getName());

			Date date = new Date();
			LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

			checkHoliday(auctionDTO.getStartDate(), auctionDTO.getEndDate());
			
			Auction newAuction = new Auction();

			if (auctionDTO.getStartDate().compareTo(localDateTime) < 0) {
				throw new RuntimeException("Start date is invalid");
			}

			if (auctionDTO.getStartDate().compareTo(auctionDTO.getEndDate()) > 0) {
				throw new RuntimeException("End date is invalid");
			}

			if (auctionDTO.getAuctionId() == null) {
				String id = generateCustomId();
				newAuction = mapper.map(auctionDTO, Auction.class);
				newAuction.setAuctionId(id);
				auctionRepo.save(newAuction);
			}

			return mapper.map(newAuction, AuctionDTO.class);
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public AuctionDTO updateAuction(AuctionDTO auctionDTO) {
		try {
			log.info("Update auction id {}", auctionDTO.getAuctionId());

			checkHoliday(auctionDTO.getStartDate(), auctionDTO.getEndDate());

			Date date = new Date();
			LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());

			if (auctionDTO.getStartDate().compareTo(localDateTime) < 0) {
				throw new RuntimeException("Start date is invalid");
			}

			if (auctionDTO.getStartDate().compareTo(auctionDTO.getEndDate()) > 0) {
				throw new RuntimeException("End date is invalid");
			}

			Auction oldAuction = auctionRepo.findById(auctionDTO.getAuctionId())
					.orElseThrow(() -> new AuctionException("Not found auction id " + auctionDTO.getAuctionId()));
			mapper.map(oldAuction, auctionDTO);
			auctionRepo.save(oldAuction);

			return mapper.map(oldAuction, AuctionDTO.class);
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public AuctionDTO findAuctionById(String auctionId) {
		try {
			log.info("Find auction id {}", auctionId);
			Auction auction = auctionRepo.findById(auctionId)
					.orElseThrow(() -> new AuctionException("Not found auction id " + auctionId));
			return mapper.map(auction, AuctionDTO.class);
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public List<AuctionDTO> findAllAuction() {
		try {
			log.info("Find all auction");
			List<Auction> listAuctions = auctionRepo.findAll();
			List<AuctionDTO> listAuctionsDTO = listAuctions.stream().map(item -> mapper.map(item, AuctionDTO.class))
					.collect(Collectors.toList());
			return listAuctionsDTO;
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public List<AuctionDTO> findAllAuctionPageable(int pageNo, int pageSize) {
		try {
			log.info("Find auction page {} and limit {}", pageNo, pageSize);
			pageNo = pageNo < 1 ? 1 : pageNo;
			Pageable pageable = PageRequest.of(pageNo - 1, 5, Sort.by(Sort.Direction.ASC, "id"));
			Page<Auction> listAuctions = auctionRepo.findAll(pageable);
			List<AuctionDTO> listAuctionsDTO = listAuctions.stream().map(item -> mapper.map(item, AuctionDTO.class))
					.collect(Collectors.toList());
			return listAuctionsDTO;
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public String deleteAuctionById(String auctionId) {
		try {
			log.info("Delete auction id {}", auctionId);
			Auction auction = auctionRepo.findById(auctionId)
					.orElseThrow(() -> new AuctionException("Not found auction id " + auctionId));

			auction.setDeleteAt(LocalDateTime.now());
			auctionRepo.save(auction);
			
			return "Delete successfully auction id " + auctionId;
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}

	@Override
	public List<AuctionDTO> findByMethodAndIsSecretAndStatus(AuctionMethod method, AuctionIsSecret isSecret,
			AuctionStatus status) {
		try {
			log.info("Find auctions by method {} and secret {} and status {}", method, isSecret, status);

			List<Auction> listAuctions = auctionRepo.findAuctionsByMethodAndSecretAndStatus(method, isSecret, status);
			List<AuctionDTO> listAuctionsDTO = listAuctions.stream().map(item -> mapper.map(item, AuctionDTO.class))
					.collect(Collectors.toList());
			return listAuctionsDTO;
		} catch (Exception e) {
			log.error("Error message: {}", e.toString());
			throw new RuntimeException(e.toString());
		}
	}
}
