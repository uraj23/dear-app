package com.mydating.dating.exceptionclasses;

public class NoSuchRecordFoundException extends RuntimeException {
	private String message;

	public NoSuchRecordFoundException(String message) {
		super(message);
	}
	
	
	

}
