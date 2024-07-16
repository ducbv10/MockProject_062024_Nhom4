package com.viettridao.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuctionDTO {

	private String auctionId;

	private String name;

	private AuctionMethod method;

	private AuctionIsSecret isSecret;

	private AuctionStatus status;

	private LocalDateTime startDate;

	private LocalDateTime endDate;
}
