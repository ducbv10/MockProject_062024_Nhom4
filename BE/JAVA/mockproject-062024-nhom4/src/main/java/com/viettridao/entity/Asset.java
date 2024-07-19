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
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@FieldDefaults(level=AccessLevel.PRIVATE)
@Table(name="Asset")
public class Asset {
	@Column(name = "AssetId", columnDefinition = "varchar(10)")
	@Id
	String assetId;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "Description")
	String description;
	
	@Column(name = "Origin")
	String origin;
	
	@Column(name = "AppraiserStatus")
	String appraiserStatus;
	
	@Column(name = "IsNew")
	String isNew;
	
	@Column(name = "IsInAuction")
	String isInauction;
	
	@Column(name = "IsSold")
	String isSold;
	
	@ManyToOne()
	@JoinColumn(name = "WarehouseId")
	WareHouse warehouse;
	
	@ManyToOne()
	@JoinColumn(name = "AppraiserId")
	Appraiser appraiser;
	
	@ManyToOne()
	@JoinColumn(name = "UserId")
	User user;
	
	@ManyToOne()
	@JoinColumn(name = "CategoryId")
	Category category;
	
	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
	
	@OneToMany(mappedBy = "asset")
	List<AuctionDetail> auctionDetails = new ArrayList<>();
	
	@OneToMany(mappedBy = "asset")
    List<Bill> bills = new ArrayList<>();

	@OneToMany(mappedBy = "asset")
	List<Media> medias = new ArrayList<>();
}
