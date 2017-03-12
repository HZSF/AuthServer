package com.weiwei.security.exception;

@SuppressWarnings("serial")
public class SmsCodeInvalidException extends UserRegistrationException {

	public SmsCodeInvalidException(String msg) {
		super(msg);
	}

	public SmsCodeInvalidException(String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
