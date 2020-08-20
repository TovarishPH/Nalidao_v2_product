package com.nalidao.v2.product.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {

	private long id;
	
	private String name;
	
	private double price;
	
	private int amount;
	
}
