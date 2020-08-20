package com.nalidao.v2.product.utils;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.ProductDto;

@Component
public class ConvertDtoToProductEntity implements Converter<ProductDto, Product> {

	@Override
	public Product convert(ProductDto source) {
		return new Product(source.getId(), source.getName(), source.getPrice(), source.getAmount());
	}

}
