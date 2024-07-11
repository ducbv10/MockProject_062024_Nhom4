package com.viettridao.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Tax;

@Repository
public interface TaxRepository extends JpaRepository<Tax, String> {
	Optional<Tax> findById(String id);
}
