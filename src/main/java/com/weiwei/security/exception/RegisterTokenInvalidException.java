package com.weiwei.security.exception;

@SuppressWarnings("serial")
public class RegisterTokenInvalidException extends UserRegistrationException {

	public RegisterTokenInvalidException(String msg) {
		super(msg);
	}
	
	public RegisterTokenInvalidException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
