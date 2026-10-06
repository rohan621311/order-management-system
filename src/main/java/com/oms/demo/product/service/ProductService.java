package com.oms.demo.product.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.oms.demo.category.entity.Category;
import com.oms.demo.category.repository.CategoryRepository;
import com.oms.demo.common.exception.BadRequestException;
import com.oms.demo.common.response.PagedResponse;
import com.oms.demo.product.dto.ProductRequest;
import com.oms.demo.product.dto.ProductResponse;
import com.oms.demo.product.entity.Product;
import com.oms.demo.product.repository.ProductRepository;
import com.oms.demo.product.repository.ProductSpecification;


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
	
	
	private static final Map<String, String> SORT_FIELDS = Map.of(
	        "id", "id",
	        "name", "name",
	        "price", "price",
	        "categoryId", "category.id",
	        "categoryName", "category.name"
	);

	private Pageable sanitize(Pageable pageable) {
		List<Sort.Order> orders = new ArrayList<>();

		for (Sort.Order order : pageable.getSort()) {
			String entityPath = SORT_FIELDS.get(order.getProperty());
			if (entityPath == null) {
				throw new BadRequestException("Cannot sort by: " + order.getProperty());
			}
			orders.add(new Sort.Order(order.getDirection(), entityPath));
		}
		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));
	}

	@Transactional(readOnly = true)
	public PagedResponse<ProductResponse> search(String name, Long categoryId,
	                                             BigDecimal minPrice, BigDecimal maxPrice,
	                                             Pageable pageable) {

	    List<Specification<Product>> filters = new ArrayList<>();

	    if (name != null && !name.isBlank()) {
	        filters.add(ProductSpecification.nameContains(name));
	    }
	    if (categoryId != null) {
	        filters.add(ProductSpecification.hasCategory(categoryId));
	    }
	    if (minPrice != null) {
	        filters.add(ProductSpecification.priceAtLeast(minPrice));
	    }
	    if (maxPrice != null) {
	        filters.add(ProductSpecification.priceAtMost(maxPrice));
	    }

	    Page<ProductResponse> page = productRepository
	            .findAll(Specification.allOf(filters), sanitize(pageable))
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
