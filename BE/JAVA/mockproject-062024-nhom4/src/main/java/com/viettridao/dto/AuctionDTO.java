package com.viettridao.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuctionDTO {

	private String auctionId;

	@NotBlank(message = "Auction's name is mandatory")
	private String name;

	private AuctionMethod method;

	private AuctionIsSecret isSecret;

	private AuctionStatus status;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime startDate;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime endDate;
}
