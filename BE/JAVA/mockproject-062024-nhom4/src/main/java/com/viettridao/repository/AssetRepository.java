package com.viettridao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Asset;

@Repository
public interface AssetRepository extends JpaRepository<Asset, String> {
	Optional<Asset> findById(String id);
}
