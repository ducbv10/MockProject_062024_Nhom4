package com.viettridao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Table(name="AuctionDetail")
public class AuctionDetail {
	
	@Id
	@Column(name = "AuctionDetailId")
	String auctionDetailId;
	
	@Column(name = "StartingPrice")
	Double startingPrice;
	
	@Column(name = "PresentPrice")
	Double presentPrice;
	
	@Column(name = "PercentPrice")
	Double percentPrice;
	
	@Column(name = "Step")
	Double step;
	
	@Column(name = "TotalIime")
	String totalTime;
	
	@ManyToOne()
	@JoinColumn(name = "WonId")
	User wonUser;
	
	@ManyToOne()
	@JoinColumn(name = "AuctionId")
	Auction auction;
	
	@ManyToOne()
	@JoinColumn(name = "AssetId")
	Asset asset;
	
	@ManyToOne()
	@JoinColumn(name = "HostId")
	User hostUser;

	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
	
	@OneToMany(mappedBy = "auctionDetail")
	List<TransactionHistory> histories = new ArrayList<>();
}
