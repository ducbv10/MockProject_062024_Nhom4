package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.ZipCode;

@Repository
public interface ZipCodeRepository extends JpaRepository<ZipCode, String> {

}
