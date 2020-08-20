package com.nalidao.v2.product.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.CreateProductDto;

/**
 * Converte um DTO de entrada em um dominio
 * @author paulo
 */
@Component
public class ConvertCreateProductDtoToEntity implements Converter<CreateProductDto, Product> {

	@Override
	public Product convert(CreateProductDto source) {
		
		return new Product(source.getName(), source.getPrice(), source.getAmount());
	}

}
