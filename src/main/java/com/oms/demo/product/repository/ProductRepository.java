package com.oms.demo.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.oms.demo.product.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

	Optional<Product> findByName(String name);
	
	
}
