package com.weiwei.security.service;

import com.weiwei.security.dto.UserDto;

public interface RegisterUserService {

	String putToCache(UserDto userDto) throws Exception; 
}
