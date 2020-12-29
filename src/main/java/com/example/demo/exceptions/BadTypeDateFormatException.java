package com.example.demo.exceptions;

public class BadTypeDateFormatException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BadTypeDateFormatException() {
	}

	public BadTypeDateFormatException(String message) {
		super(message);
	}
	
}
