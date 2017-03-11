package com.weiwei.security.service.impl;

import java.util.UUID;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.common.Constants;
import com.weiwei.security.dto.UserDto;
import com.weiwei.security.exception.UserOnRegisteringException;
import com.weiwei.security.service.RegisterUserService;

import bitronix.tm.BitronixTransactionManager;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

	@Autowired
	private BitronixTransactionManager transactionManager;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public String putToCache(UserDto userDto) throws Exception {
		Cache<String, UserDto> userDtoCache = cacheManager.getCache(Constants.CACHE_REGISTER_USER, String.class,
				UserDto.class);
		String registerToken = generateToken();

		transactionManager.begin();
		if (userDtoCache.containsKey(userDto.getUsername())) {
			throw new UserOnRegisteringException("Another customer is registering a same username!");
		}
		Cache<String, String> usernameCache = cacheManager.getCache(Constants.CACHE_TOKEN_USERNAME, String.class,
				String.class);
		while (usernameCache.containsKey(registerToken)) {
			registerToken = generateToken();
		}
		userDtoCache.put(userDto.getUsername(), userDto);
		usernameCache.put(registerToken, userDto.getUsername());
		transactionManager.commit();

		return registerToken;
	}

	private String generateToken() {
		return UUID.randomUUID().toString().toUpperCase();
	}

}
