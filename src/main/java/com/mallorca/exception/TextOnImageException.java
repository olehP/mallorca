package com.mallorca.exception;

public class TextOnImageException extends RuntimeException {

	public TextOnImageException() {
		super();
	}

	public TextOnImageException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TextOnImageException(String message, Throwable cause) {
		super(message, cause);
	}

	public TextOnImageException(String message) {
		super(message);
	}

	public TextOnImageException(Throwable cause) {
		super(cause);
	}

}
