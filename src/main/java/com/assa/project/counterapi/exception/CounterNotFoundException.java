package com.assa.project.counterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CounterNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public CounterNotFoundException(String message) {
		super(message);
	}

}
