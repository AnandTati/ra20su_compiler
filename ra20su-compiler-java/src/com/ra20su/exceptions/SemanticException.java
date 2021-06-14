package com.ra20su.exceptions;

public class SemanticException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SemanticException() {
		super();
	}

	public SemanticException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SemanticException(String message, Throwable cause) {
		super(message, cause);
	}

	public SemanticException(String message) {
		super(message);
	}

	public SemanticException(Throwable cause) {
		super(cause);
	}

}
