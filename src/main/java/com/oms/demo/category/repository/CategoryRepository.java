package com.oms.demo.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.demo.category.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>{

	Optional<Category> findByName(String name);

}
