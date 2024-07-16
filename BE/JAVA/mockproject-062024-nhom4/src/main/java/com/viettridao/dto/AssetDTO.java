package com.viettridao.dto;

import com.viettridao.entity.enums.AppraiserStatus;
import com.viettridao.entity.enums.AssetIsInAuction;
import com.viettridao.entity.enums.AssetIsNew;
import com.viettridao.entity.enums.IsSold;

public class AssetDTO {

	private String assetId;

	private String name;

	private String description;

	private String origin;

	private AppraiserStatus appraiserStatus;

	private AssetIsNew isNew;

	private IsSold isSold;

	private AssetIsInAuction isInAuction;

	private UserDTO userId;

	private AppraiserDTO appraiserId;

	private WareHouseDTO wareHouseId;

	private CategoryDTO categoryId;

	public AssetDTO() {
		super();
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public AppraiserStatus getAppraiserStatus() {
		return appraiserStatus;
	}

	public void setAppraiserStatus(AppraiserStatus appraiserStatus) {
		this.appraiserStatus = appraiserStatus;
	}

	public AssetIsNew getIsNew() {
		return isNew;
	}

	public void setIsNew(AssetIsNew isNew) {
		this.isNew = isNew;
	}

	public IsSold getIsSold() {
		return isSold;
	}

	public void setIsSold(IsSold isSold) {
		this.isSold = isSold;
	}

	public AssetIsInAuction getIsInAuction() {
		return isInAuction;
	}

	public void setIsInAuction(AssetIsInAuction isInAuction) {
		this.isInAuction = isInAuction;
	}

	public UserDTO getUserId() {
		return userId;
	}

	public void setUserId(UserDTO userId) {
		this.userId = userId;
	}

	public AppraiserDTO getAppraiserId() {
		return appraiserId;
	}

	public void setAppraiserId(AppraiserDTO appraiserId) {
		this.appraiserId = appraiserId;
	}

	public WareHouseDTO getWareHouseId() {
		return wareHouseId;
	}

	public void setWareHouseId(WareHouseDTO wareHouseId) {
		this.wareHouseId = wareHouseId;
	}

	public CategoryDTO getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(CategoryDTO categoryId) {
		this.categoryId = categoryId;
	}
}
