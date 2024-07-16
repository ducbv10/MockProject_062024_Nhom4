package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.WareHouse;

@Repository
public interface WareHouseRepository extends JpaRepository<WareHouse, String> {

}
