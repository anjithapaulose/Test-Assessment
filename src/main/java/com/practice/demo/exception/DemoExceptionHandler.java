package com.practice.demo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Exception handler
 * 
 * @author Anjitha
 *
 */
@ControllerAdvice
public class DemoExceptionHandler extends ResponseEntityExceptionHandler {
	
	// Exception handler for DemoException
	@ExceptionHandler(DemoException.class)
	public final ResponseEntity<?> handleDemoException(DemoException ex) {
		Map<String, String> error = new HashMap<>();
		error.put("errorMessage", ex.getMessage());
		return new ResponseEntity<Map<String, String>>(error, ex.getHttpStatus());
	}

	// @Valid invalid scenario handling
	@Override
	public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ResponseEntity<>(errors, status);
	}
}
