package com.oms.demo.product.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.demo.category.entity.Category;
import com.oms.demo.category.repository.CategoryRepository;
import com.oms.demo.common.response.PagedResponse;
import com.oms.demo.product.dto.ProductRequest;
import com.oms.demo.product.dto.ProductResponse;
import com.oms.demo.product.entity.Product;
import com.oms.demo.product.repository.ProductRepository;


@Service
public class ProductService {

	private final ProductRepository productRepository;
	
	private final CategoryRepository categoryRepository;
	
	public ProductService(ProductRepository productRepository,CategoryRepository categoryRepository) {
		this.productRepository=productRepository;
		this.categoryRepository=categoryRepository;
	}
	
	@Transactional
	public ProductResponse create (ProductRequest productRequest) {
		
		 if (productRepository.findByName(productRequest.getName()).isPresent()) {
	            throw new RuntimeException("Product already exists: " + productRequest.getName());
	        }
		 
		 Category category = categoryRepository.findById(productRequest.getCategoryId())
	                .orElseThrow(() -> new RuntimeException("Category not found: " + productRequest.getCategoryId()));
		 
		 Product product= Product.builder()
				 .name(productRequest.getName())
				 .description(productRequest.getDescription())
				 .price(productRequest.getPrice())
				 .category(category)
				 .build();
		 
		 return ProductResponse.fromEntity(productRepository.save(product));
		
	}
	
	@Transactional(readOnly = true)
	public PagedResponse<ProductResponse> getall(Pageable pageable){
		
		Page<ProductResponse> page =productRepository.findAll(pageable)
				.map(ProductResponse::fromEntity);
		
		return PagedResponse.from(page);
		
	}
	
	@Transactional(readOnly = true)
	public ProductResponse getById(Long id) {
		Product product= productRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Product not found: "+id));
		
		return ProductResponse.fromEntity(product);
	}
	
	@Transactional
	public ProductResponse update(Long id, ProductRequest productRequest) {
		
		Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found: " + id));

        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found: " + productRequest.getCategoryId()));
        
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setCategory(category);
        
        return ProductResponse.fromEntity(productRepository.save(product));
		
	}
	
	@Transactional
	 public void delete(Long id) {
	        if (!productRepository.existsById(id)) {
	            throw new RuntimeException("Product not found: " + id);
	        }
	        productRepository.deleteById(id);
	    }
	
	
}
