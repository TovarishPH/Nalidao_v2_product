package com.nalidao.v2.product.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.nalidao.v2.product.domain.Product;
import com.nalidao.v2.product.domain.dto.ProductDto;

/**
 * Converte um dompinio em um dto
 * @author paulo
 */
@Component
public class ConvertProductEntityToDto implements Converter<Product, ProductDto> {

	@Override
	public ProductDto convert(Product source) {
		return new ProductDto(source.getId(), source.getName(), source.getPrice(), source.getAmount());
	}

	public List<ProductDto> convertList(List<Product> source) {
		List<ProductDto> dtoList = new ArrayList<ProductDto>();
		source.forEach(s -> {
			ProductDto dto = new ProductDto(s.getId(), s.getName(), s.getPrice(), s.getAmount());
			dtoList.add(dto);
		});
		return dtoList;
	}
}
