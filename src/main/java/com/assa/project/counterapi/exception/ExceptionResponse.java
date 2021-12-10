package com.assa.project.counterapi.exception;

import java.util.Date;

public class ExceptionResponse {
	
	//When an exception happens I want to return it in this format
	//when the exception happened
	private Date timestamp;
	
	//Exception message
	private String message;
	
	//some details
	private String details;

	public ExceptionResponse(Date timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}


}
