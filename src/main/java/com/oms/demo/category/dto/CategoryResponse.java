package com.oms.demo.category.dto;

import com.oms.demo.category.entity.Category;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryResponse {
	
	
	private Long id;
	private String name;
	private String description;
	
	public static CategoryResponse fromEntity(Category category) {
		CategoryResponse categoryResponse= new CategoryResponse();
		categoryResponse.setId(category.getId());
		categoryResponse.setName(category.getName());
		categoryResponse.setDescription(category.getDescription());
		
		return categoryResponse;
	}

}
