package com.nalidao.v2.product.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.CreateProductDto;
import com.nalidao.v2.product.domain.dto.ProductDto;
import com.nalidao.v2.product.service.ProductService;
import com.nalidao.v2.product.utils.ConvertCreateProductDtoToEntity;
import com.nalidao.v2.product.utils.ConvertDtoToProductEntity;
import com.nalidao.v2.product.utils.ConvertProductEntityToDto;

/**
 * Controller da api de produto
 * @author paulo
 */
@RestController
@RequestMapping("/product-api")
public class ProductController {
	
	@Autowired
	private ProductService service;
	
	@Autowired
	private ConvertProductEntityToDto productToDtoConverter;
	
	@Autowired
	private ConvertDtoToProductEntity productDtoToEntity;
	
	@Autowired
	private ConvertCreateProductDtoToEntity createProductConverter;
	
	@GetMapping
	public List<ProductDto> getAllProduct() {
		return this.productToDtoConverter.convertList(this.service.findall());
	}

	@GetMapping("/{id}")
	public ProductDto getProductById(@PathVariable final long id) {
		Product product = this.service.getProductById(id).get();
		return this.productToDtoConverter.convert(product);
	}
	
	@PostMapping
	public ResponseEntity<ProductDto> createProduct(@RequestBody final CreateProductDto dto, UriComponentsBuilder builder) {
		Product entity = new Product();
		entity = this.service.createProduct(this.createProductConverter.convert(dto));
		ProductDto productDto = this.productToDtoConverter.convert(entity);
		URI uri = builder.path("/product-api/{id}").buildAndExpand(productDto.getId()).toUri();
		return ResponseEntity.created(uri).body(productDto);
	}
	
	@PutMapping
	public ResponseEntity<ProductDto> updateProduct(@RequestBody ProductDto dto) {
		Product product = this.productDtoToEntity.convert(dto);
		ProductDto responseDto = this.productToDtoConverter.convert(this.service.updateProduct(product));
		return ResponseEntity.ok(responseDto);
	}
}
