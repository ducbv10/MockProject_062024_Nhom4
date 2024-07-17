package com.viettridao.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuctionDetailRequest {

	private String auctionDetailId;

	@NotBlank(message = "AuctionId is not null.")
	private String auctionId;

	private double startingPrice;

	private double presentPrice;

	private double percentPrice;

	private double step;

	private String wonId;

	private String hostId;

	private String totaltime;

	private String assetId;
}
