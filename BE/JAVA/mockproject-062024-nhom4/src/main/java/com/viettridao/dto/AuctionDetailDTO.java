package com.viettridao.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDetailDTO {

	String auctionDetailId;

	Double startingPrice;

	Double presentPrice;

	Double percentPrice;

	Double step;

	String totalTime;

	UserDTO wonUser;

	AuctionDTO auction;

	AssetDTO asset;

	UserDTO hostUser;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	LocalDateTime deleteAt;
}
