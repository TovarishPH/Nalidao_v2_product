package com.nalidao.v2.product.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.github.javafaker.Faker;
import com.nalidao.v2.product.domain.Product;

public class TestUtils {
	
	public Faker getFaker() {
		return new Faker();
	}
	
	public Product getProduct() {
		return new Product(1L,
				getFaker().lorem().characters(7),
				getFaker().random().nextDouble(),
				getFaker().random().nextInt(0,100));
	}

	public List<Product> getProductList() {
		List<Product> list = new ArrayList<Product>();
		list.add(new Product(1L,
				getFaker().funnyName().toString(),
				new Random().nextDouble(),
				new Random().nextInt()));
		
		list.add(new Product(2L,
				getFaker().funnyName().toString(),
				new Random().nextDouble(),
				new Random().nextInt()));
		
		return list;
	}
}
