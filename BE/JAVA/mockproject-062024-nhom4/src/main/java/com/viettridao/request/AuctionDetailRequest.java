package com.viettridao.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuctionDetailRequest {

	private String auctionDetailId;

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
