package com.viettridao.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WareHouseDTO {

	private String wareHouseId;

	private String position;

	private double quantity;

	private Date importDate;

	private Date exportDate;
}
