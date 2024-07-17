package com.viettridao.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.viettridao.dto.AssetDTO;
import com.viettridao.dto.AuctionDTO;
import com.viettridao.dto.AuctionDetailDTO;
import com.viettridao.dto.UserDTO;
import com.viettridao.entity.Asset;
import com.viettridao.entity.Auction;
import com.viettridao.entity.AuctionDetail;
import com.viettridao.entity.User;
import com.viettridao.repository.AssetRepository;
import com.viettridao.repository.AuctionRepository;
import com.viettridao.repository.UserRepository;
import com.viettridao.repository.auctionDetailRepository;
import com.viettridao.request.AuctionDetailRequest;
import com.viettridao.service.impl.AuctionDetailServiceImpl;

@SpringBootTest
public class AuctionDetailServiceTests {

	@Spy
	@Autowired
	private AuctionDetailServiceImpl auctionDetailService;

	@MockBean
	private auctionDetailRepository auctionDetailRepo;

	@MockBean
	private UserRepository userRepo;

	@MockBean
	private AssetRepository assetRepo;

	@MockBean
	private AuctionRepository auctionRepo;

	private AuctionDetailRequest request;
	private AuctionDetailDTO auctionDetailDTO;
	private AuctionDetail auctionDetail;
	private UserDTO winnerDTO;
	private UserDTO hostDTO;
	private User winner;
	private User host;
	private AssetDTO assetDTO;
	private Asset asset;
	private AuctionDTO auctionDTO;
	private Auction auction;

	@BeforeEach
	void init() {
	    MockitoAnnotations.openMocks(this);

	    request = AuctionDetailRequest.builder().auctionDetailId("ADC0000001").auctionId("AC00000001")
	            .startingPrice(100000.0).presentPrice(10000.0).percentPrice(10.0).step(5).wonId("US00000001")
	            .hostId("US00000002").totaltime("3").assetId("AS00000001").build();

	    auctionDTO = AuctionDTO.builder().auctionId("AC00000001").build();
	    
	    auction = Auction.builder().auctionId("AC00000001").build();

	    winnerDTO = UserDTO.builder().userId("US00000001").build();

	    winner = User.builder().userId("US00000001").build();

	    hostDTO = UserDTO.builder().userId("US00000002").build();

	    host = User.builder().userId("US00000002").build();

	    assetDTO = AssetDTO.builder().assetId("AS00000001").build();

	    asset = Asset.builder().assetId("AS00000001").build();

	    auctionDetailDTO = AuctionDetailDTO.builder().auctionDetailId("ADC0000001").auction(auctionDTO)
	            .startingPrice(100000.0).presentPrice(10000.0).percentPrice(10.0).step(5.0).wonUser(winnerDTO)
	            .hostUser(hostDTO).totalTime("3").asset(assetDTO).build();

	    auctionDetail = AuctionDetail.builder().auctionDetailId("ADC0000001").auction(auction).startingPrice(100000.0)
	            .presentPrice(10000.0).percentPrice(10.0).step(5.0).wonUser(winner).hostUser(host).totalTime("3")
	            .asset(asset).build();
	}


//	Test create ----------------------------------------------------------------------------------------------
	@Test
	void createNewAuctionDetail_validRequest_success() {
	    request.setAuctionDetailId(null);

	    when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
	    when(userRepo.findById("US00000001")).thenReturn(Optional.of(winner));
	    when(userRepo.findById("US00000002")).thenReturn(Optional.of(host));
	    when(assetRepo.findById("AS00000001")).thenReturn(Optional.of(asset));

	    when(auctionDetailRepo.save(ArgumentMatchers.any())).thenReturn(auctionDetail);

	    var response = auctionDetailService.createNewAuctionDetail(request);

	    assertThat(response.getAuction().getAuctionId()).isEqualTo("AC00000001");
	}

	@Test
	void createNewAuctionDetail_invalidRequest_auctionIdIsNull_failed() {
		request.setAuctionDetailId(null);
		auctionDetail.setAuctionDetailId(null);
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		var exception = assertThrows(RuntimeException.class, () -> auctionDetailService.createNewAuctionDetail(request));
		assertThat(exception.getMessage())
			.isEqualTo("com.viettridao.exception.NotFoundException: Not found auction id AC00000001");
	}
	
	@Test
	void createNewAuctionDetail_invalidRequest_wonIdIsNull_failed() {
		request.setAuctionDetailId(null);
		auctionDetail.setAuctionDetailId(null);
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
		when(userRepo.findById(request.getWonId())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> auctionDetailService.createNewAuctionDetail(request));
	}
	
	@Test
	void createNewAuctionDetail_invalidRequest_hostIdIsNull_failed() {
		request.setAuctionDetailId(null);
		auctionDetail.setAuctionDetailId(null);
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
		when(userRepo.findById(request.getWonId())).thenReturn(Optional.of(winner));
		when(userRepo.findById(request.getHostId())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> auctionDetailService.createNewAuctionDetail(request));
	}
	
	@Test
	void createNewAuctionDetail_invalidRequest_assetIdIsNull_failed() {
		request.setAuctionDetailId(null);
		auctionDetail.setAuctionDetailId(null);
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
		when(userRepo.findById(request.getWonId())).thenReturn(Optional.of(winner));
		when(userRepo.findById(request.getHostId())).thenReturn(Optional.of(host));
		when(assetRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> auctionDetailService.createNewAuctionDetail(request));
	}
	
//	Test update ----------------------------------------------------------------------------------------------
	@Test
	void updateAuctionDetail_validRequest_success() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));
	    when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
	    when(userRepo.findById("US00000001")).thenReturn(Optional.of(winner));
	    when(userRepo.findById("US00000002")).thenReturn(Optional.of(host));
	    when(assetRepo.findById("AS00000001")).thenReturn(Optional.of(asset));

	    when(auctionDetailRepo.save(ArgumentMatchers.any())).thenReturn(auctionDetail);

	    var response = auctionDetailService.updateAuctionDetail(request);

	    assertThat(response.getAuction().getAuctionId()).isEqualTo("AC00000001");
	}
	
	@Test
	void updateAuctionDetail_notFoundAuctionDetail_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		var exception = assertThrows(RuntimeException.class, () -> auctionDetailService.updateAuctionDetail(request));
		assertThat(exception.getMessage())
			.isEqualTo("com.viettridao.exception.NotFoundException: Not found auction detail id ADC0000001");
	}
	
	@Test
	void updateAuctionDetail_invalidRequest_auctionIdIsNull_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		var exception = assertThrows(RuntimeException.class, () -> auctionDetailService.updateAuctionDetail(request));
		assertThat(exception.getMessage())
			.isEqualTo("com.viettridao.exception.NotFoundException: Not found auction id AC00000001");
	}
	
	@Test
	void updateAuctionDetail_invalidRequest_wonIdIsNull_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
		when(userRepo.findById(request.getWonId())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> auctionDetailService.updateAuctionDetail(request));
	}
	
	@Test
	void updateAuctionDetail_invalidRequest_hostIdIsNull_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
		when(userRepo.findById(request.getWonId())).thenReturn(Optional.of(winner));
		when(userRepo.findById(request.getHostId())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> auctionDetailService.updateAuctionDetail(request));
	}
	
	@Test
	void updateAuctionDetail_invalidRequest_assetIdIsNull_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));
		when(auctionRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auction));
		when(userRepo.findById(request.getWonId())).thenReturn(Optional.of(winner));
		when(userRepo.findById(request.getHostId())).thenReturn(Optional.of(host));
		when(assetRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());
		
		assertThrows(RuntimeException.class, () -> auctionDetailService.updateAuctionDetail(request));
	}
	
//	Test delete ----------------------------------------------------------------------------------------------
	@Test
	void deleteAuctionDetailById_success() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));

	    when(auctionDetailRepo.save(ArgumentMatchers.any())).thenReturn(auctionDetail);

	    var response = auctionDetailService.deleteAuctionDetailById(auctionDetailDTO.getAuctionDetailId());

	    assertThat(response).isEqualTo("Delete auction detail id ADC0000001");
	}
	
	@Test
	void deleteAuctionDetailById_notFound_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

	    var exception = assertThrows(RuntimeException.class, 
	    		() -> auctionDetailService.deleteAuctionDetailById(auctionDetailDTO.getAuctionDetailId()));

	    assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: Not found auction detail id ADC0000001");
	}
	
//	Test find by id ----------------------------------------------------------------------------------------------
	@Test
	void findAuctionDetailById_success() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.of(auctionDetail));

	    var response = auctionDetailService.findAuctionDetailById("ADC0000001");

	    assertThat(response.getAuctionDetailId()).isEqualTo("ADC0000001");
	}
	
	@Test
	void findAuctionDetailById_notFound_failed() {
		when(auctionDetailRepo.findById(ArgumentMatchers.any())).thenReturn(Optional.empty());

		var exception = assertThrows(RuntimeException.class, 
	    		() -> auctionDetailService.findAuctionDetailById("ADC0000001"));

	    assertThat(exception.getMessage()).isEqualTo("java.lang.RuntimeException: Not found auction detail id ADC0000001");
	}
}
