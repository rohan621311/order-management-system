package com.oms.demo.category.controller;

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

import com.oms.demo.category.dto.CategoryRequest;
import com.oms.demo.category.dto.CategoryResponse;
import com.oms.demo.category.service.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	private final CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService=categoryService;
	}
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public CategoryResponse create(@Valid @RequestBody CategoryRequest categoryRequest) {
		return categoryService.create(categoryRequest);
	}
	
	@GetMapping
	public List<CategoryResponse> getAll(){
		return categoryService.getAll();
	}
	
	
	@GetMapping("/{id}")
	public CategoryResponse getById(@PathVariable Long id) {
		return categoryService.getById(id);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/{id}")
	public CategoryResponse update(@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest  ) {
		return categoryService.update(id, categoryRequest);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		categoryService.delete(id);
	}

}
