package com.nalidao.v2.product.errorhandling;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nalidao.v2.product.errorhandling.exception.ProductNotFoundException;
import com.nalidao.v2.product.errorhandling.utils.ApiErrorDetails;

@ControllerAdvice
public class ApiErrorHandling {

	@ExceptionHandler(ProductNotFoundException.class)
	private ResponseEntity<ApiErrorDetails> handlePorductNotFoundException(ProductNotFoundException e) {
		ApiErrorDetails details = new ApiErrorDetails("ProductNotFound",
													HttpStatus.NOT_FOUND.toString(),
													e.getLocalizedMessage(),
													e.getClass().getPackage().toString(),
													LocalDateTime.now());
		return new ResponseEntity<ApiErrorDetails>(details, HttpStatus.NOT_FOUND);
	}
}
