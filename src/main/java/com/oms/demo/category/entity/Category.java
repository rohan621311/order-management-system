package com.oms.demo.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="CATEGORIES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "category_seq")
	@SequenceGenerator(name="category_seq",sequenceName = "CATEGORY_SEQ",allocationSize = 1)
	private Long id;
	
	@Column(nullable = false,unique = true)
	private String name;
	
	private String description;

}
