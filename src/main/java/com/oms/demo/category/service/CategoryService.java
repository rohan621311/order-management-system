package com.oms.demo.category.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.oms.demo.category.dto.CategoryRequest;
import com.oms.demo.category.dto.CategoryResponse;
import com.oms.demo.category.entity.Category;
import com.oms.demo.category.repository.CategoryRepository;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;
	
	public CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository=categoryRepository;
	}
	
	public CategoryResponse create(CategoryRequest categoryRequest) {
		
		if(categoryRepository.findByName(categoryRequest.getName()).isPresent()) {
			throw new RuntimeException("Category already exists: "+categoryRequest.getName());
		}
		
		Category category= Category.builder()
				.name(categoryRequest.getName())
				.description(categoryRequest.getDescription())
				.build();
		
		return CategoryResponse.fromEntity(categoryRepository.save(category));
		
	}
	
	public List<CategoryResponse> getAll(){
		return categoryRepository.findAll().stream().map(CategoryResponse::fromEntity)
				.toList();
	}
	
	public CategoryResponse getById(Long id){
		Category category = categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found: "+id));

		return CategoryResponse.fromEntity(category);
	}
	
	public CategoryResponse update(Long id, CategoryRequest request) {
		
		Category category= categoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Category not found: "+id));
		
		category.setName(request.getName());
		category.setDescription(request.getDescription());
		return CategoryResponse.fromEntity(categoryRepository.save(category));
		
	}
	
	public void delete(Long id) {
		if(!categoryRepository.existsById(id)) {
			throw new RuntimeException("Category not found: " + id);
		}
		
		categoryRepository.deleteById(id);
	}

}
