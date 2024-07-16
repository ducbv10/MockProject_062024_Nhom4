package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.State;

@Repository
public interface StateRepository extends JpaRepository<State, String> {

}
