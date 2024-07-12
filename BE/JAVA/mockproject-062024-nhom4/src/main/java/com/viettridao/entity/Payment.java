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
@Table(name="Payment")
public class Payment {
	@Id
	@Column(name = "PaymentId")
	String paymentId;
	
	@ManyToOne()
	@JoinColumn(name = "UserId")
	User user;
	
	@Column(name = "BankName")
	String bankName;
	
	@Column(name = "BankNum")
	String bankNum;
	
	@Column(name = "BankBranch")
	String bankBranch;
	
	@Column(name = "AccountBalance")
	Double accountBalance;

	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
}