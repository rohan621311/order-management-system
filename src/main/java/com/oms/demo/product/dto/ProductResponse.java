package com.oms.demo.product.dto;

import java.math.BigDecimal;

import com.oms.demo.product.entity.Product;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

	private Long id;

	private String name;

	private String description;

	private BigDecimal price;

	private Long categoryId;

	private String categoryName;

	public static ProductResponse fromEntity(Product product) {
		ProductResponse response = new ProductResponse();

		response.setId(product.getId());
		response.setName(product.getName());
		response.setDescription(product.getDescription());
		response.setPrice(product.getPrice());
		response.setCategoryId(product.getCategory().getId());
		response.setCategoryName(product.getCategory().getName());
		
		return response;

	}

}
