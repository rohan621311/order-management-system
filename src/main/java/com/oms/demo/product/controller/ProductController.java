package com.oms.demo.product.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.oms.demo.product.dto.ProductRequest;
import com.oms.demo.product.dto.ProductResponse;
import com.oms.demo.product.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	private final ProductService productService;
	
	public ProductController(ProductService productService) {
		this.productService=productService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ProductResponse create(@Valid @RequestBody ProductRequest productRequest) {
		 return productService.create(productRequest);
	}
	
	@GetMapping
	public List<ProductResponse> getall(){
		return productService.getall();
	}
	
	@GetMapping("/{id}")
	public ProductResponse getById(@PathVariable Long id) {
		return productService.getById(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public ProductResponse update(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
		return productService.update(id, productRequest);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		productService.delete(id);
	}
	

}
