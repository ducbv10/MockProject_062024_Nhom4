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
@Table(name="Tax")
public class Tax {
	@Column(name = "TaxId")
	@Id
	String taxId;
	
	@Column(name = "TypeTax")
	String typeTax;
	
	@Column(name = "Location")
	String location;
	
	@Column(name = "Value")
	Double value;
	
	@ManyToOne()
	@JoinColumn(name = "UserId")
	User user;

	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
	
	@OneToMany(mappedBy = "tax")
	List<Bill> bills = new ArrayList<Bill>();
}
