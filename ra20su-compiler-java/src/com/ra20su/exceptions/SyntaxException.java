package com.ra20su.exceptions;

public class SyntaxException extends CompilerException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SyntaxException() {
		super();
	}

	public SyntaxException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SyntaxException(String message, Throwable cause) {
		super(message, cause);
	}

	public SyntaxException(String message) {
		super(message);
	}

	public SyntaxException(Throwable cause) {
		super(cause);
	}

	public SyntaxException(String message, int lineNumber) {
		super(message + " at line = " + lineNumber);
	}

}
