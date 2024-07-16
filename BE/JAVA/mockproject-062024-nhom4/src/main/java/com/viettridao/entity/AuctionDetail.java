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

	public String getAuctionDetailId() {
		return auctionDetailId;
	}

	public void setAuctionDetailId(String auctionDetailId) {
		this.auctionDetailId = auctionDetailId;
	}

	public Double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(Double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Double getPresentPrice() {
		return presentPrice;
	}

	public void setPresentPrice(Double presentPrice) {
		this.presentPrice = presentPrice;
	}

	public Double getPercentPrice() {
		return percentPrice;
	}

	public void setPercentPrice(Double percentPrice) {
		this.percentPrice = percentPrice;
	}

	public Double getStep() {
		return step;
	}

	public void setStep(Double step) {
		this.step = step;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public User getWonUser() {
		return wonUser;
	}

	public void setWonUser(User wonUser) {
		this.wonUser = wonUser;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public User getHostUser() {
		return hostUser;
	}

	public void setHostUser(User hostUser) {
		this.hostUser = hostUser;
	}

	public LocalDateTime getDeleteAt() {
		return deleteAt;
	}

	public void setDeleteAt(LocalDateTime deleteAt) {
		this.deleteAt = deleteAt;
	}

	public List<TransactionHistory> getHistories() {
		return histories;
	}

	public void setHistories(List<TransactionHistory> histories) {
		this.histories = histories;
	}
}
