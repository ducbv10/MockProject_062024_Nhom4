package com.viettridao.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="Bill")
public class Bill {
	@Column(name = "BillId")
	@Id
	private String billId;
	
	@Column(name = "TotalAmount")
	private Double totalAmount;
	
	@Column(name = "Status")
	private String status;
	
	@Column(name = "PaymentTime")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paymentTime;
	
	@ManyToOne()
	@JoinColumn(name = "UserId")
	private User user;
	
	@ManyToOne()
	@JoinColumn(name = "TaxId")
	private Tax tax;
	
	@ManyToOne()
    @JoinColumn(name = "AssetId")
    private Asset asset;

	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime deleteAt;
	
}
