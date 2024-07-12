package com.viettridao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
@Table(name="[User]")
public class User {
	
	@Column(name = "UserId")
	@Id
	String userId;
	
	@Column(name = "UserName")
	String username;
	
	@Column(name = "PassWord")
	String password;
	
	@Column(name = "Avatar")
	String avatar;
	
	@Column(name = "Name")
	String name;
	
	@Column(name = "DateOfBirth")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Date dob;
	
	@Column(name = "Gender")
	String gender;
	
	@Column(name = "Phone")
	String phone;
	
	@Column(name = "Email")
	String email;
	
	@Column(name = "IsBan")
	String isBan;
	
	@Column(name = "Verify")
	String verify;
	
	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
	
	@ManyToOne()
	@JoinColumn(name = "AddressId")
	Address address;
	
	@OneToMany(mappedBy = "user")
	List<UserRole> userRoles = new ArrayList<UserRole>();
	
	@OneToMany(mappedBy = "user")
	List<Asset> assets = new ArrayList<Asset>();
	
	@OneToMany(mappedBy = "user")
	List<Tax> taxs = new ArrayList<Tax>();
	
	@OneToMany(mappedBy = "user")
	List<Bill> bills = new ArrayList<Bill>();

	@OneToMany(mappedBy = "user")
	List<Notification> notifications = new ArrayList<Notification>();
	
	@OneToMany(mappedBy = "user")
	List<Payment> payments = new ArrayList<Payment>();
	
	@OneToMany(mappedBy = "wonUser")
	List<AuctionDetail> auctionDetails1 = new ArrayList<AuctionDetail>();
	
	@OneToMany(mappedBy = "hostUser")
	List<AuctionDetail> auctionDetails2 = new ArrayList<AuctionDetail>();
	
	@OneToMany(mappedBy = "user")
	List<TransactionHistory> histories = new ArrayList<TransactionHistory>();
}
