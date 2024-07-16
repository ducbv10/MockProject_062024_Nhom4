package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {

}
