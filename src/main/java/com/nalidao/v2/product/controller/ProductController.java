package com.nalidao.v2.product.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller da api de produto
 * @author paulo
 */
@RestController
@RequestMapping("/product-api")
@Api(value = "Api de Produtos")
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
	@ApiOperation(value = "Lista todos os produtos cadastrados")
	public List<ProductDto> getAllProduct() {
		return this.service.findall();
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "Busca por produto baseada no id do mesmo")
	public ResponseEntity<ProductDto> getProductById(@PathVariable("id") final long id) {
		ProductDto product = this.service.getProductById(id);
		return ResponseEntity.ok(product);
	}
	
	@PostMapping
	@ApiOperation(value = "Cria um novo produto na base de dados")
	public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid final CreateProductDto dto, UriComponentsBuilder builder) {
		ProductDto productDto = this.service.createProduct(dto);
		URI uri = builder.path("/product-api/{id}").buildAndExpand(productDto.getId()).toUri();
		return ResponseEntity.created(uri).body(productDto);
	}
	@PutMapping
	@ApiOperation(value = "Update de produto previamente cadastrado")
	public ResponseEntity<ProductDto> updateProduct(@RequestBody @Valid final ProductDto dto) {
		ProductDto responseDto = this.service.updateProduct(dto);
		return ResponseEntity.ok(responseDto);
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Deletea um produto da base de dados")
	public ResponseEntity<?> removeProduct(@PathVariable("id") long id) {
		this.service.removeProduct(id);
		return ResponseEntity.ok("Produto id " + id + " removido da base de dados.");
	}
}
