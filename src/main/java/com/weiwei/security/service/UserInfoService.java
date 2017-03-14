package com.weiwei.security.service;

public interface UserInfoService {

	String putToCache(String email, String username) throws Exception;
	
}
