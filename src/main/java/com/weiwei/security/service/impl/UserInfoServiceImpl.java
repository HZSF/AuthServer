package com.weiwei.security.service.impl;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.weiwei.common.Constants;
import com.weiwei.security.exception.EmailOnSavingException;
import com.weiwei.security.service.UserInfoService;
import com.weiwei.utils.RandomGenerator;

import bitronix.tm.BitronixTransactionManager;

@Service
public class UserInfoServiceImpl implements UserInfoService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private BitronixTransactionManager transactionManager;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public String putToCache(String email, String username) throws Exception {
		Cache<String, String> tokenEmailCache = cacheManager.getCache(Constants.CACHE_TOKEN_EMAIL, String.class,
				String.class);
		Cache<String, String> emailUsernameCache = cacheManager.getCache(Constants.CACHE_EMAIL_USERNAME, String.class,
				String.class);
		String confirmToken = RandomGenerator.generateToken();

		transactionManager.begin();
		try {
			if (emailUsernameCache.containsKey(username)) {
				throw new EmailOnSavingException("Another customer is registering a same username!");
			}
			while (tokenEmailCache.containsKey(confirmToken)) {
				confirmToken = RandomGenerator.generateToken();
			}
			tokenEmailCache.put(confirmToken, email);
			emailUsernameCache.put(email, username);
		} finally {
			transactionManager.commit();
		}
		logger.info("({},{}) put to cache!", email, username);
		return confirmToken;
	}

}
