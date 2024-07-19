package com.viettridao.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viettridao.dto.AssetDTO;
import com.viettridao.entity.Asset;
import com.viettridao.entity.User;
import com.viettridao.exception.NotFoundException;
import com.viettridao.repository.AssetRepository;
import com.viettridao.repository.AuctionRepository;
import com.viettridao.repository.UserRepository;
import com.viettridao.service.IAssetService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AssetServiceImpl implements IAssetService {

	@Autowired
	private AssetRepository assetRepository;
	
	@Autowired
	private AuctionRepository auctionRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper mapper;
	
	@Override
	public List<AssetDTO> findAssetByWonId(String userId) {
		try {
			User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Not found user id " + userId));
			List<Asset> listAssets = assetRepository.findByWonId(user);
			List<AssetDTO> listAssetsDTO = listAssets.stream()
					.map(item -> mapper.map(item, AssetDTO.class))
					.collect(Collectors.toList());
			return listAssetsDTO;
		} catch (Exception e) {
			throw new RuntimeException(e.toString());
		}
	}
}
