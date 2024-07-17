package com.viettridao.controller;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.viettridao.dto.AuctionDTO;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;
import com.viettridao.service.impl.AuctionServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class AuctionControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuctionServiceImpl auctionService;

	private AuctionDTO auctionDTO;

	private LocalDateTime date;

	@BeforeEach
	void init() {
		date = LocalDateTime.of(2024, 7, 16, 0, 0, 0);

		auctionDTO = AuctionDTO.builder().auctionId("AC00000001").name("Auction Products").method(AuctionMethod.on)
				.isSecret(AuctionIsSecret.secret).status(AuctionStatus.upcoming).startDate(date).endDate(date).build();
	}

	@Test
	void createAuction_success() throws Exception {
		// GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(auctionDTO);

		when(auctionService.createNewAuction(ArgumentMatchers.any())).thenReturn(auctionDTO);

		// WHEN, THEN
		mockMvc.perform(
				MockMvcRequestBuilders.post("/auctions").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect((ResultMatcher) MockMvcResultMatchers
						.jsonPath("message", "").value("Create new auction name Auction Products"));
	}

	@Test
	void createAuction_nameIsNull_failed() throws Exception {
		// GIVEN
		auctionDTO.setName(null);
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(auctionDTO);

		// WHEN, THEN
		mockMvc.perform(
				MockMvcRequestBuilders.post("/auctions").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateAuction_success() throws Exception {
		// GIVEN
		auctionDTO.setEndDate(LocalDateTime.of(2024, 7, 20, 0, 0, 0));
		auctionDTO.setName("Auction Update");
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(auctionDTO);

		when(auctionService.updateAuction(ArgumentMatchers.any())).thenReturn(auctionDTO);

		// WHEN, THEN
		mockMvc.perform(
				MockMvcRequestBuilders.put("/auctions").contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk()).andExpect((ResultMatcher) MockMvcResultMatchers
						.jsonPath("message", "").value("Update auction name Auction Update"));
	}

	@Test
	void findAuctionId_success() throws Exception {
		// GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(auctionDTO);

		when(auctionService.findAuctionById(ArgumentMatchers.any())).thenReturn(auctionDTO);

		// WHEN, THEN
		mockMvc.perform(MockMvcRequestBuilders.get("/auctions/AC00000001").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.auctionId").value("AC00000001"));
	}

	@Test
	void findAuctionByStatusAndSecretAndMethod_success() throws Exception {
		// GIVEN
		List<AuctionDTO> listAuctionDTOs = new ArrayList<>();
		listAuctionDTOs.add(auctionDTO);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(listAuctionDTOs);

		when(auctionService.findByMethodAndIsSecretAndStatus(ArgumentMatchers.any(), ArgumentMatchers.any(),
				ArgumentMatchers.any())).thenReturn(listAuctionDTOs);

		// WHEN, THEN
		mockMvc.perform(MockMvcRequestBuilders.get("/auctions?method=&isSecret=&status=")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void getAllAuctions_success() throws Exception {
		// GIVEN
		List<AuctionDTO> listAuctionDTOs = new ArrayList<>();
		listAuctionDTOs.add(auctionDTO);
		
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(listAuctionDTOs);

		when(auctionService.findAllAuction()).thenReturn(listAuctionDTOs);

		// WHEN, THEN
		mockMvc.perform(MockMvcRequestBuilders.get("/auctions/all")
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	void deleteAuctionById_success() throws Exception {
		// GIVEN
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerModule(new JavaTimeModule());
		String content = objectMapper.writeValueAsString(auctionDTO);

		// WHEN, THEN
		mockMvc.perform(
				MockMvcRequestBuilders.delete("/auctions/" + auctionDTO.getAuctionId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk());
	}
}
