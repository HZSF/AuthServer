package com.weiwei.security.service;

import java.util.Optional;

import com.weiwei.security.dto.RegisterValidateRequest;
import com.weiwei.security.dto.UserDto;

public interface RegisterUserService {

	String putToCache(UserDto userDto, String smscode) throws Exception;

	Optional<UserDto> retrieveThenRemoveFromCache(RegisterValidateRequest request) throws Exception;
	
}
