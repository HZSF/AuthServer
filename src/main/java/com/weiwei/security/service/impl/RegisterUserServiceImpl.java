package com.weiwei.security.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.codehaus.jackson.map.ObjectMapper;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.common.Constants;
import com.weiwei.security.dto.RegisterValidateRequest;
import com.weiwei.security.dto.UserDto;
import com.weiwei.security.exception.RegisterTokenInvalidException;
import com.weiwei.security.exception.SmsCodeInvalidException;
import com.weiwei.security.exception.UserOnRegisteringException;
import com.weiwei.security.service.RegisterUserService;
import com.weiwei.security.vo.UsernameTuple;

import bitronix.tm.BitronixTransactionManager;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BitronixTransactionManager transactionManager;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public String putToCache(UserDto userDto, String smscode) throws Exception {
		Cache<String, String> userDtoCache = cacheManager.getCache(Constants.CACHE_REGISTER_USER, String.class,
				String.class);
		Cache<String, String> usernameTupleCache = cacheManager.getCache(Constants.CACHE_TOKEN_USERNAME, String.class,
				String.class);
		String registerToken = generateToken();
		ObjectMapper mapper = new ObjectMapper();
		String jsonStrUserDto = mapper.writeValueAsString(userDto);
		String jsonStrUsernameTuple = mapper.writeValueAsString(new UsernameTuple(userDto.getUsername(), smscode));

		System.out.println(smscode);

		transactionManager.begin();
		try {
			if (userDtoCache.containsKey(userDto.getUsername())) {
				throw new UserOnRegisteringException("Another customer is registering a same username!");
			}
			while (usernameTupleCache.containsKey(registerToken)) {
				registerToken = generateToken();
			}
			userDtoCache.put(userDto.getUsername(), jsonStrUserDto);
			usernameTupleCache.put(registerToken, jsonStrUsernameTuple);
		} finally {
			transactionManager.commit();
		}

		return registerToken;
	}

	private String generateToken() {
		return UUID.randomUUID().toString().toUpperCase();
	}

	@Override
	public Optional<UserDto> retrieveThenRemoveFromCache(RegisterValidateRequest request) throws Exception {
		String tokenKey = request.getVtoken();
		Cache<String, String> userDtoCache = cacheManager.getCache(Constants.CACHE_REGISTER_USER, String.class,
				String.class);
		Cache<String, String> usernameTupleCache = cacheManager.getCache(Constants.CACHE_TOKEN_USERNAME, String.class,
				String.class);
		ObjectMapper mapper = new ObjectMapper();
		String jsonStrUserDto = "";
		String jsonStrUsernameTuple = "";
		UserDto userDto = null;
		
		transactionManager.begin();
		try {
			jsonStrUsernameTuple = usernameTupleCache.get(tokenKey);
			if (jsonStrUsernameTuple == null) {
				throw new RegisterTokenInvalidException("Token invalid!");
			}
			logger.info("Pass token validation!");
			
			UsernameTuple usernameTuple = mapper.readValue(jsonStrUsernameTuple, UsernameTuple.class);
			if (!usernameTuple.getSmsCode().equals(request.getSmscode())) {
				throw new SmsCodeInvalidException("smscode invalid!");
			}
			logger.info("Pass sms code validation!");
			
			String username = usernameTuple.getUsername();
			jsonStrUserDto = userDtoCache.get(username);
			userDto = mapper.readValue(jsonStrUserDto, UserDto.class);
			logger.info("Got userDto from cache!");
			
			//Remove current record from cache
			usernameTupleCache.remove(tokenKey);
			userDtoCache.remove(username);
			logger.info("Removed current record from cache!");
		} finally {
			transactionManager.commit();
		}
		
		return Optional.of(userDto);
	}

}
