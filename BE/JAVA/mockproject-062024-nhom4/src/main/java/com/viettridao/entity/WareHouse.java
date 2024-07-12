package com.viettridao.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="WareHouse")
public class WareHouse {
	@Column(name = "WareHouseId", columnDefinition = "varchar(10)")
	@Id
	String warehouseId;
	
	@OneToMany(mappedBy = "warehouse")
	List<Asset> assets = new ArrayList<Asset>();
	
	@Column(name = "Position")
	String position;
	
	@Column(name = "Quantity")
	Double quantity;
	
	@Column(name = "ImportDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime importDate;
	
	@Column(name = "ExportDate")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime exportDate;

	@Column(name = "DeletedAt")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	LocalDateTime deleteAt;
}
