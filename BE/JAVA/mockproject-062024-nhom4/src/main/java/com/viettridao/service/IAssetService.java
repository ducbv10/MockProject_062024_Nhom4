package com.viettridao.service;

import java.util.List;

import com.viettridao.dto.AssetDTO;

public interface IAssetService {

	List<AssetDTO> findAssetByWonId(String userId);
}
