package com.practice.demo.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception class
 * 
 * @author Anjitha
 *
 */
public class DemoException extends RuntimeException {
	private HttpStatus httpStatus;
	private String message;
	
	public DemoException(HttpStatus httpStatus,String message) {
		this.httpStatus = httpStatus;
		this.message = message;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public String getMessage() {
		return message;
	}

}
