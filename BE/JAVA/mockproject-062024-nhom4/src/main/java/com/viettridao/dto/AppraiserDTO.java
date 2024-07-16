package com.viettridao.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppraiserDTO {

	private String appraiserId;

	private String name;

	private String experience;

	private String email;

	private String phone;

	private String address;
}
