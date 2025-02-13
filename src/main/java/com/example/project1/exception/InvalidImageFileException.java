package com.example.project1.exception;

public class InvalidImageFileException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InvalidImageFileException(String message) {
        super(message);
    }
}

