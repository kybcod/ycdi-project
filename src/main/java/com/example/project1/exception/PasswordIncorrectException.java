package com.example.project1.exception;

public class PasswordIncorrectException extends RuntimeException {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private final int failedAttempts;
	private final String id;

    public PasswordIncorrectException(String message, int failedAttempts, String id) {
        super(message);
        this.failedAttempts = failedAttempts;
		this.id = id;
    }

    public int getFailedAttempts() {
        return failedAttempts;
    }

	public String getId() {
		return id;
	}
    
    
}
