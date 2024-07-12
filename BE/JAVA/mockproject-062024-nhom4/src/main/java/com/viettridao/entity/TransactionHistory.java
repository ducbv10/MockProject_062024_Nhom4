package com.viettridao.entity;

import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="TransactionHistory")
public class TransactionHistory {
	@Id
	@Column(name = "TransactionHistoryId")
	String transactionHistoryId;
	
	@Column(name = "TradeDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime tradeDate;
	
	@Column(name = "TradeCost")
	Double tradeCost;
	
	@Column(name = "DepositCost")
	Double depositCost;
	
	@Column(name = "Note")
	String note;
	
	@Column(name = "Status")
	String status;
	
	@ManyToOne()
	@JoinColumn(name = "UserId")
	User user;
	
	@ManyToOne()
	@JoinColumn(name = "AuctionDetailId")
	AuctionDetail auctionDetail;

	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
}
