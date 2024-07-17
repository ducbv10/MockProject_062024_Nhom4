package com.viettridao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.viettridao.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {

}
