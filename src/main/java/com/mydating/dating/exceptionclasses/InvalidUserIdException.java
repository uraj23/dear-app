package com.mydating.dating.exceptionclasses;

public class InvalidUserIdException extends RuntimeException {

	private String message;

	public InvalidUserIdException(String message) {
		super(message);
	}

}
