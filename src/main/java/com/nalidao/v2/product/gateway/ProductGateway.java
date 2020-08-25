package com.nalidao.v2.product.gateway;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.repository.ProductRepository;

@Component
public class ProductGateway {
	
	@Autowired
	private ProductRepository repository;

	public Optional<Product> findProductById(long id) {
		return this.repository.findById(id);
	}

	public List<Product> findAll() {
		return this.repository.findAll();
	}

	public Product saveProduct(Product entity) {
		return this.repository.save(entity);
	}

	public void removeProduct(Long id) {
		this.repository.deleteById(id);
	}
	
	
}
