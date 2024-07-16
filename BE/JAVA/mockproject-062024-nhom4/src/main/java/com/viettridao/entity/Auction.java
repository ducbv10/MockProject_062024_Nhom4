package com.viettridao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viettridao.entity.enums.AuctionIsSecret;
import com.viettridao.entity.enums.AuctionMethod;
import com.viettridao.entity.enums.AuctionStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
@Table(name="Auction")
public class Auction {
	@Id
	@Column(name = "AuctionId")
	String auctionId;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Method")
	@Enumerated(EnumType.STRING)
	AuctionMethod method;
	
	@Column(name = "IsSecret")
	@Enumerated(EnumType.STRING)
	AuctionIsSecret isSecret;
	
	@Column(name = "Status")
	@Enumerated(EnumType.STRING)
	AuctionStatus status;
	
	@Column(name = "StartDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime startDate;
	
	@Column(name = "EndDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime endDate;
	
	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
	
	@OneToMany(mappedBy = "auction")
	List<AuctionDetail> auctionDetails = new ArrayList<>();
}
