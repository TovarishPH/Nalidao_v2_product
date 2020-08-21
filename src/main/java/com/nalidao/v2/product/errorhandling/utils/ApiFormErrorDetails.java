package com.nalidao.v2.product.errorhandling.utils;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ApiFormErrorDetails extends ApiErrorDetails {

	private String field;

	public ApiFormErrorDetails(String title, String status, String message, String packagePath, LocalDateTime timastamp,
			String field) {
		super(title, status, message, packagePath, timastamp);
		this.field = field;
	}
	
	
}
