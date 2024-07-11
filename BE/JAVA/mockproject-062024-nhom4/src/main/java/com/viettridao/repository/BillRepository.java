package com.viettridao.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Bill;
import com.viettridao.entity.User;

@Repository
public interface BillRepository extends JpaRepository<Bill, String> {
	Optional<Bill> findById(String id);
	
	List<Bill> findAllByUser(User user, Pageable pageable);
	
	List<Bill> findAllByDeleteAt(LocalDateTime deleteAt, Pageable pageable);
}
