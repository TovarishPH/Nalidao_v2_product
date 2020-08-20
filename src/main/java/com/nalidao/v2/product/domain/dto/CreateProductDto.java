package com.nalidao.v2.product.domain.dto;

import lombok.Data;

@Data
public class CreateProductDto {

	private String name;

	private double price;

	private int amount;
}
