package com.weiwei.security.exception;

@SuppressWarnings("serial")
public class UserRegistrationException extends RuntimeException {

	public UserRegistrationException(String msg) {
		super(msg);
	}

	public UserRegistrationException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
