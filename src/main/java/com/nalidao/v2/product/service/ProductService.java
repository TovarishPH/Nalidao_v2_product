package com.nalidao.v2.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.CreateProductDto;
import com.nalidao.v2.product.domain.dto.ProductDto;
import com.nalidao.v2.product.errorhandling.exception.ProductNotFoundException;
import com.nalidao.v2.product.gateway.ProductGateway;
import com.nalidao.v2.product.utils.ConvertCreateProductDtoToEntity;
import com.nalidao.v2.product.utils.ConvertDtoToProductEntity;
import com.nalidao.v2.product.utils.ConvertProductEntityToDto;


/**
 * Classe de serviço da api de produto
 * @author paulo
 */
@Service
public class ProductService {

	@Autowired
	private ProductGateway gateway;
	
	@Autowired
	private ConvertProductEntityToDto productToDtoConverter;
	@Autowired
	private ConvertCreateProductDtoToEntity createProductDtoToEntityConverter;
	@Autowired
	private ConvertDtoToProductEntity productDtoToEntity;
	
	
	public List<ProductDto> findall() {
		return this.productToDtoConverter.convertList(this.gateway.findAll());
	}

	public ProductDto getProductById(final long id) {
		Optional<Product> product = this.gateway.findProductById(id);
		if(product.isPresent()) {
			return this.productToDtoConverter.convert(product.get());
		}
		
		throw new ProductNotFoundException("Produto com id " + id + " não encontrado na base de dados");
	}

	public ProductDto createProduct(final CreateProductDto dto) {
		Product product = this.gateway.saveProduct(this.createProductDtoToEntityConverter.convert(dto));
		ProductDto prodDto = this.productToDtoConverter.convert(product);
		return prodDto;
	}

	public ProductDto updateProduct(final ProductDto productDto) {
		Optional<Product> entity = this.gateway.findProductById(productDto.getId());
		if(entity.isPresent()) {
			Product prod = this.productDtoToEntity.convert(productDto);
			
			return this.productToDtoConverter.convert(this.gateway.saveProduct(prod));
		}
		
		throw new ProductNotFoundException("Id " + productDto.getId() + " não encontrado na base de dados, para atualização de produto.");
	}

	public void removeProduct(final long id) {
		Optional<Product> product = this.gateway.findProductById(id);
		if(product.isPresent()) {
			this.gateway.removeProduct(id);
		} else {
			throw new ProductNotFoundException("Id " + id + " não encontrado. Não é possível efetuar a remoção deste produto.");
		}
	}

}
