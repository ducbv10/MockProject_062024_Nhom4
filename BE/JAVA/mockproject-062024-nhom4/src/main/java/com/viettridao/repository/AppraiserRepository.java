package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Appraiser;

@Repository
public interface AppraiserRepository extends JpaRepository<Appraiser, String> {

}
