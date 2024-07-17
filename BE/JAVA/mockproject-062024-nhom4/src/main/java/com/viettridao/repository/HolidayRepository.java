package com.viettridao.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Holiday;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday, String> {

	@Query(value = "SELECT h.holidayName FROM Holiday h WHERE :date BETWEEN h.holidayStart AND h.holidayEnd")
	String findBetweenHolidayStartAndHolidayEnd(@Param("date") LocalDateTime date);
}
