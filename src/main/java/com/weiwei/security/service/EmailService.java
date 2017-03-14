package com.weiwei.security.service;

public interface EmailService {

	void sendConfirmEmail(String emailAddress, String confirmToken, String username);
	
}
