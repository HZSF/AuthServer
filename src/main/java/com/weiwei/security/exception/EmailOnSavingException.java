package com.weiwei.security.exception;

@SuppressWarnings("serial")
public class EmailOnSavingException extends RuntimeException {

	public EmailOnSavingException(String msg) {
		super(msg);
	}

	public EmailOnSavingException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
