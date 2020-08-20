package com.nalidao.v2.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.ProductDto;
import com.nalidao.v2.product.gateway.ProductGateway;


/**
 * Classe de serviço da api de produto
 * @author paulo
 */
@Service
public class ProductService {

	@Autowired
	private ProductGateway gateway;

	public Optional<Product> getProductById(long id) {
		return this.gateway.findProductById(id);
	}

	public List<Product> findall() {
		return this.gateway.findall();
	}

	public Product createProduct(Product entity) {
		try {
			Product product = this.gateway.createProduct(entity);
			return product;
		} catch (Exception e) {
			System.out.println("Deu merda!");
		}
		return null;
	}

}
