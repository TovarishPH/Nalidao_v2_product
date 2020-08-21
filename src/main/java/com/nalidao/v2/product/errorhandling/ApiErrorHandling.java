package com.nalidao.v2.product.errorhandling;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.nalidao.v2.product.errorhandling.exception.ProductNotFoundException;
import com.nalidao.v2.product.errorhandling.utils.ApiErrorDetails;
import com.nalidao.v2.product.errorhandling.utils.ApiFormErrorDetails;

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
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	private ResponseEntity<List<ApiFormErrorDetails>> handleArgumentoNotValidException(MethodArgumentNotValidException e) {
		List<ApiFormErrorDetails> details = new ArrayList<ApiFormErrorDetails>();
		
		List<FieldError> fields = e.getBindingResult().getFieldErrors();
		fields.forEach(f -> {
			ApiFormErrorDetails detail = new ApiFormErrorDetails("ArgumentNotValid", 
																HttpStatus.BAD_REQUEST.toString(), 
																e.getLocalizedMessage(), 
																e.getParameter().toString(), 
																LocalDateTime.now(), 
																f.getField());
			details.add(detail);
		});
		return new ResponseEntity<List<ApiFormErrorDetails>>(details, HttpStatus.BAD_REQUEST);
	}
}
