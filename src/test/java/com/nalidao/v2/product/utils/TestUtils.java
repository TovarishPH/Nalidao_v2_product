package com.nalidao.v2.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;
import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.CreateProductDto;
import com.nalidao.v2.product.domain.dto.ProductDto;

public class TestUtils {
	
	public Faker getFaker() {
		return new Faker();
	}
	
	public Product getProduct() {
//		return new Product(1L,
//				getFaker().lorem().characters(7),
//				getFaker().random().nextDouble(),
//				getFaker().random().nextInt(0,100));
		return new Product(1L,
				getFaker().funnyName().name(),
				getFaker().random().nextDouble(),
				getFaker().random().nextInt(0,100));
	}

	public List<Product> getProductList() {
		List<Product> list = new ArrayList<Product>();
		list.add(new Product(1L,
				getFaker().funnyName().name(),
				new Random().nextDouble(),
				new Random().nextInt()));
		
		list.add(new Product(2L,
				getFaker().funnyName().name(),
				new Random().nextDouble(),
				new Random().nextInt()));
		
		return list;
	}
	
	public ProductDto getProductDto() {
//		return new ProductDto(1L,
//				getFaker().lorem().characters(7),
//				getFaker().random().nextDouble(),
//				getFaker().random().nextInt(0,100));
		return new ProductDto(1L,
				getFaker().funnyName().name(),
				getFaker().random().nextDouble(),
				getFaker().random().nextInt(0,100));
	}
	
	public List<ProductDto> getProductDtoList() {
		List<ProductDto> dtoList = new ArrayList<ProductDto>();
		dtoList.add(new ProductDto(1L,
				getFaker().funnyName().name(),
				new Random().nextDouble(),
				new Random().nextInt()));
		
		dtoList.add(new ProductDto(2L,
				getFaker().funnyName().name(),
				new Random().nextDouble(),
				new Random().nextInt()));
		
		return dtoList;
	}
	
	public CreateProductDto getCreateProductDto() {
		return new CreateProductDto(getFaker().funnyName().name(),
									getFaker().random().nextDouble(),
									getFaker().random().nextInt(0,100));
	}
}
