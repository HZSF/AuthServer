package com.weiwei.security.service.impl;

import java.util.UUID;

import org.ehcache.Cache;
import org.springframework.stereotype.Service;

import com.weiwei.common.Constants;
import com.weiwei.security.dto.UserDto;
import com.weiwei.security.exception.UserOnRegisteringException;
import com.weiwei.security.exception.UserRegistrationException;
import com.weiwei.security.service.RegisterUserService;
import com.weiwei.util.ApplicationResource;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

	@Override
	public synchronized String putToCache(UserDto userDto) throws UserRegistrationException {
		Cache<String, UserDto> userDtoCache = ApplicationResource.cacheManager.getCache(Constants.CACHE_REGISTER_USER,
				String.class, UserDto.class);
		if (userDtoCache.containsKey(userDto.getUsername())) {
			throw new UserOnRegisteringException("Another customer is registering a same username!");
		}
		Cache<String, String> usernameCache = ApplicationResource.cacheManager.getCache(Constants.CACHE_TOKEN_USERNAME,
				String.class, String.class);
		String registerToken = generateToken();
		while (usernameCache.containsKey(registerToken)) {
			registerToken = generateToken();
		}
		usernameCache.put(registerToken, userDto.getUsername());
		userDtoCache.put(userDto.getUsername(), userDto);
		return registerToken;
	}
	
	private String generateToken() {
		return UUID.randomUUID().toString().toUpperCase();
	}

}
