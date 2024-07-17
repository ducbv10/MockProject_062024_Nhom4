package com.viettridao.controller;

import static org.mockito.Mockito.when;

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
import com.viettridao.dto.AuctionDTO;
import com.viettridao.dto.AuctionDetailDTO;
import com.viettridao.request.AuctionDetailRequest;
import com.viettridao.service.impl.AuctionDetailServiceImpl;

@SpringBootTest
@AutoConfigureMockMvc
public class AuctionDetailControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuctionDetailServiceImpl auctionDetailService;

	private AuctionDetailRequest request;
	private AuctionDetailDTO result;
	private AuctionDTO auction;

	@BeforeEach
	void init() {
		request = AuctionDetailRequest.builder().auctionDetailId("ADC0000001").auctionId("AC00000001")
				.startingPrice(100000.0).presentPrice(10000.0).percentPrice(10.0).step(5).wonId("US00000001")
				.hostId("US00000002").totaltime("3").assetId("AS00000001").build();

		auction = AuctionDTO.builder().auctionId("AC00000001").build();

		result = AuctionDetailDTO.builder().auctionDetailId("ADC0000001").auction(auction).startingPrice(100000.0)
				.presentPrice(10000.0).percentPrice(10.0).step(5.0).wonUser(null).hostUser(null).totalTime("3")
				.asset(null).build();
	}

	@Test
	void createNewAuctionDetail_success() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(request);

		when(auctionDetailService.createNewAuctionDetail(ArgumentMatchers.any())).thenReturn(result);

		mockMvc.perform(MockMvcRequestBuilders.post("/auction-detail").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("message", "").value("New auction detail"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.auctionDetailId").value("ADC0000001"));
	}

	@Test
	void createNewAuctionDetail_invalidRequest_failed() throws Exception {
		request.setAuctionId(null);
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(request);

		mockMvc.perform(MockMvcRequestBuilders.post("/auction-detail").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content)).andExpect(MockMvcResultMatchers.status().isBadRequest());
	}

	@Test
	void updateAuctionDetail_success() throws Exception {
		request.setTotaltime("5");
		result.setTotalTime("5");
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(request);

		when(auctionDetailService.updateAuctionDetail(ArgumentMatchers.any())).thenReturn(result);

		mockMvc.perform(MockMvcRequestBuilders.put("/auction-detail").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("message", "").value("Update auction detail"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.totalTime").value("5"));
	}

	@Test
	void updateAuctionDetail_invalidRequest_failed() throws Exception {
		request.setAuctionId(null);
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(request);

		mockMvc.perform(MockMvcRequestBuilders.put("/auction-detail").contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(content)).andExpect(MockMvcResultMatchers.status().isBadRequest())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("status", "").value(400));
	}

	@Test
	void deleteAuctionDetail_success() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(request);

		when(auctionDetailService.deleteAuctionDetailById(ArgumentMatchers.any()))
				.thenReturn("Delete auction detail id " + request.getAuctionDetailId());

		mockMvc.perform(MockMvcRequestBuilders.delete("/auction-detail/" + request.getAuctionDetailId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("status", "").value(200))
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("message", "").value("Delete auction detail"));
	}

	@Test
	void findAuctionDetailById_success() throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();
		String content = objectMapper.writeValueAsString(request);

		when(auctionDetailService.findAuctionDetailById(ArgumentMatchers.any())).thenReturn(result);

		mockMvc.perform(MockMvcRequestBuilders.get("/auction-detail/" + request.getAuctionDetailId())
				.contentType(MediaType.APPLICATION_JSON_VALUE).content(content))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("status", "").value(200))
				.andExpect((ResultMatcher) MockMvcResultMatchers.jsonPath("message", "").value("Find auction detail"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.data.auctionDetailId", "").value("ADC0000001"));
	}
}
