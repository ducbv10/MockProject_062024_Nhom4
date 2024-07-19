package com.viettridao.dto;

import com.viettridao.entity.enums.AppraiserStatus;
import com.viettridao.entity.enums.AssetIsInAuction;
import com.viettridao.entity.enums.AssetIsNew;
import com.viettridao.entity.enums.IsSold;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssetDTO {

	private String assetId;

	private String name;

	private String description;

	private String origin;

	private AppraiserStatus appraiserStatus;

	private AssetIsNew isNew;

	private IsSold isSold;

	private AssetIsInAuction isInAuction;

//	private AppraiserDTO appraiserId;
//
//	private WareHouseDTO wareHouseId;
//
//	private CategoryDTO categoryId;
}
