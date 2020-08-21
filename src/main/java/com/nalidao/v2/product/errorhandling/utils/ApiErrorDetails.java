package com.nalidao.v2.product.errorhandling.utils;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiErrorDetails {
	
	private String title;
	private String status;
	private String message;
	private String packagePath;
	private LocalDateTime timastamp;
	
}
