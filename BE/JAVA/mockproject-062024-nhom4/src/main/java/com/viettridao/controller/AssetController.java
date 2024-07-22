package com.viettridao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viettridao.response.ResponseData;
import com.viettridao.response.ResponseError;
import com.viettridao.service.impl.AssetServiceImpl;

@RestController
@RequestMapping("/assets")
public class AssetController {

	@Autowired
	private AssetServiceImpl assetService;
	
	@GetMapping("/userId/{userId}")
	public ResponseData<?> getAssetByWinner(@PathVariable("userId") String userId) {
		try {
			return new ResponseData<>(HttpStatus.OK.value(), "Get assets by winner",
					assetService.findAssetByWonId(userId));
		} catch (Exception e) {
			return new ResponseError<>(HttpStatus.BAD_REQUEST.value(), e.getMessage());
		}
	}
}
