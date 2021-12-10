package com.assa.project.counterapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class IncrementWrongException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public IncrementWrongException(String message) {
		super(message);
	}

}
