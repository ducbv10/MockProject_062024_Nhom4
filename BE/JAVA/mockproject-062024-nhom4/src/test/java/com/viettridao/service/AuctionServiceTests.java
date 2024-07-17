package com.viettridao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.viettridao.dto.AuctionDTO;
import com.viettridao.entity.Auction;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;
import com.viettridao.repository.AuctionRepository;
import com.viettridao.repository.HolidayRepository;
import com.viettridao.service.impl.AuctionServiceImpl;

@SpringBootTest
public class AuctionServiceTests {

	@Autowired
	private AuctionServiceImpl auctionService;
	
	@MockBean
	private AuctionRepository auctionRepository;
	
	@MockBean
	private HolidayRepository holidayRepository;
	
	private AuctionDTO auctionDTO;
	
	private Auction auction;

	private LocalDateTime date;

	@BeforeEach
	void init() {
		date = LocalDateTime.of(2025, 7, 16, 0, 0, 0);

		auctionDTO = AuctionDTO.builder().auctionId("AC00000001").name("Auction Products").method(AuctionMethod.on)
				.isSecret(AuctionIsSecret.secret).status(AuctionStatus.upcoming).startDate(date).endDate(date).build();
		
		auction = Auction.builder().auctionId("AC00000001").name("Auction Products").method(AuctionMethod.on)
				.isSecret(AuctionIsSecret.secret).status(AuctionStatus.upcoming).startDate(date).endDate(date).build();
	}
	
	//	Test create ----------------------------------------------------------------------
	@Test
	void createAuction_validRequest_success() {
		auctionDTO.setAuctionId(null);
		when(auctionRepository.save(any())).thenReturn(auction);
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn(null);
		
		var response = auctionService.createNewAuction(auctionDTO);
		
		assertThat(response.getName()).isEqualTo(auction.getName());
	}
	
	@Test
	void createAuction_invalidStartDate_failed() {
		auctionDTO.setAuctionId(null);
		auctionDTO.setStartDate(LocalDateTime.of(2024, 7, 16, 0, 0, 0));
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn(null);
		
		var exception =  assertThrows(RuntimeException.class, () -> auctionService.createNewAuction(auctionDTO));
		assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: Start date is invalid");
	}
	
	@Test
	void createAuction_invalidEndDate_failed() {
		auctionDTO.setAuctionId(null);
		auctionDTO.setEndDate(LocalDateTime.of(2024, 7, 16, 0, 0, 0));
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn(null);
		
		var exception =  assertThrows(RuntimeException.class, () -> auctionService.createNewAuction(auctionDTO));
		assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: End date is invalid");
	}
	
	@Test
	void createAuction_duplicateHoliday_failed() {
		auctionDTO.setAuctionId(null);
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn("Holiday");
		
		var exception =  assertThrows(RuntimeException.class, () -> auctionService.createNewAuction(auctionDTO));
		assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: com.viettridao.exception.HolidayException: 2025-07-16T00:00 is Holiday");
	}
	
	//	Test update ----------------------------------------------------------------------
	@Test
	void updateAuction_validRequest_success() {
		auctionDTO.setName("Update name");
		auction.setName("Update name");
		when(auctionRepository.save(any())).thenReturn(auction);
		when(auctionRepository.findById(any())).thenReturn(Optional.of(auction));
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn(null);
		
		var response = auctionService.updateAuction(auctionDTO);
		
		assertThat(response.getName()).isEqualTo("Update name");
	}
	
	@Test
	void updateAuction_invalidStartDate_failed() {
		auctionDTO.setStartDate(LocalDateTime.of(2024, 7, 16, 0, 0, 0));
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn(null);
		
		var exception =  assertThrows(RuntimeException.class, () -> auctionService.updateAuction(auctionDTO));
		assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: Start date is invalid");
	}
	
	@Test
	void updateAuction_invalidEndDate_failed() {
		auctionDTO.setEndDate(LocalDateTime.of(2024, 7, 16, 0, 0, 0));
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn(null);
		
		var exception =  assertThrows(RuntimeException.class, () -> auctionService.updateAuction(auctionDTO));
		assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: End date is invalid");
	}
	
	@Test
	void updateAuction_duplicateHoliday_failed() {
		when(holidayRepository.findBetweenHolidayStartAndHolidayEnd(any())).thenReturn("Holiday");
		
		var exception =  assertThrows(RuntimeException.class, () -> auctionService.updateAuction(auctionDTO));
		assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: com.viettridao.exception.HolidayException: 2025-07-16T00:00 is Holiday");
	}
	
	//	Test find by id ----------------------------------------------------------------------
	@Test
	void findAuctionById_success() {
		when(auctionRepository.findById(any())).thenReturn(Optional.of(auction));
		
		var response = auctionService.findAuctionById(auctionDTO.getAuctionId());
		
		assertThat(response.getAuctionId()).isEqualTo(auction.getAuctionId());
	}
	
	@Test
	void findAuctionById_notFound_failed() {
		when(auctionRepository.findById(any())).thenReturn(Optional.empty());
		
		var exception = assertThrows(RuntimeException.class, () -> auctionService.findAuctionById(auctionDTO.getAuctionId()));
		
		assertThat(exception.getMessage()).isEqualTo("com.viettridao.exception.AuctionException: Not found auction id AC00000001");
	}
	
	//	Test find all----------------------------------------------------------------------
	@Test
	void findAllAuction_success() {
		List<Auction> listAuctions = new ArrayList<>();
		listAuctions.add(auction);
		
		when(auctionRepository.findAll()).thenReturn(listAuctions);
		
		var response = auctionService.findAllAuction();
		
		assertThat(response.size()).isEqualTo(1);
	}
	
	@Test
	void findAllAuctionPageable_success() {
		int page = 0;
		int size = 5;
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
		List<Auction> listAuctions = new ArrayList<>();
		listAuctions.add(auction);
		Page<Auction> auctionPage = new PageImpl<>(listAuctions, pageable, listAuctions.size());
		
		when(auctionRepository.findAll(pageable)).thenReturn(auctionPage);
		
		var response = auctionService.findAllAuctionPageable(page, size);
		assertThat(response.size()).isEqualTo(1);
	}
	
	//	Test delete----------------------------------------------------------------------
	@Test
	void deleteAuctionById_success() {
		when(auctionRepository.findById(auctionDTO.getAuctionId())).thenReturn(Optional.of(auction));
		
		var response = auctionService.deleteAuctionById(auctionDTO.getAuctionId());
		assertThat(response).isEqualTo("Delete successfully auction id " + auctionDTO.getAuctionId());
	}
	
	@Test
	void deleteAuctionById_notFound_failed() {
		when(auctionRepository.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		var exception = assertThrows(RuntimeException.class, () -> auctionService.deleteAuctionById(auctionDTO.getAuctionId()));
		assertThat(exception.getMessage()).isEqualTo("com.viettridao.exception.AuctionException: Not found auction id AC00000001");
	}
	
	//	Test find with filter----------------------------------------------------------------------
	@Test
	void findByMethodAndIsSecretAndStatus_success() {
		AuctionMethod method = null;
		AuctionIsSecret isSecret = AuctionIsSecret.secret;
		AuctionStatus status = AuctionStatus.upcoming;
		
		List<Auction> listAuctions = new ArrayList<>();
		listAuctions.add(auction);
		
		when(auctionRepository.findAuctionsByMethodAndSecretAndStatus(method, isSecret, status)).thenReturn(listAuctions);
		
		var response = auctionService.findByMethodAndIsSecretAndStatus(method, isSecret, status);
		assertThat(response.size()).isEqualTo(1);
	}
}
