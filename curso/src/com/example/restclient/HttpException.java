package com.example.restclient;

public class HttpException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private final int statusCode;
	
	private static void validate(int statusCode) {
		if(0 > statusCode || statusCode > 520)
			throw new IllegalArgumentException("Invalid status code");
		
	}
	public HttpException(int statusCode) {
		this(statusCode, "");
	}

	public HttpException(int statusCode, String message) {
		validate(statusCode);
		super(message);
		this.statusCode = statusCode;
	}

	public HttpException(int statusCode, Throwable cause) {
		validate(statusCode);
		super(cause);
		this.statusCode = statusCode;
	}

	public HttpException(int statusCode, String message, Throwable cause) {
		validate(statusCode);
		super(message, cause);
		this.statusCode = statusCode;
	}

	public HttpException(int statusCode, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		validate(statusCode);
		super(message, cause, enableSuppression, writableStackTrace);
		this.statusCode = statusCode;
	}
	
	public int getStatusCode() {
		return statusCode;
	}

}
