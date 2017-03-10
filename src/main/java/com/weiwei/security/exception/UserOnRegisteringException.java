package com.weiwei.security.exception;

/**
 * Exception indicating that a username is doing registration currently.
 * 
 */

@SuppressWarnings("serial")
public class UserOnRegisteringException extends UserRegistrationException {

	public UserOnRegisteringException(String msg) {
		super(msg);
	}
	
	public UserOnRegisteringException(String msg, Throwable cause) {
		super(msg, cause);
	}

}
