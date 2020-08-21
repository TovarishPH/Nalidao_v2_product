package com.nalidao.v2.product.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class CreateProductDto {

	@NotEmpty(message = "'Name' não pode ser vazio.")
	@Length(min = 4, message = "'Name' deve conter ao menos 4 caracteres.")
	private String name;

	@Positive(message = "'Price' deve ser maior que zero.")
	private double price;

	@PositiveOrZero(message = "'Amount' não pode ser um número negativo.")
	private int amount;
}
