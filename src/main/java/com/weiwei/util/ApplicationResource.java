package com.weiwei.util;

import java.util.concurrent.TimeUnit;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;

import com.weiwei.common.Constants;
import com.weiwei.security.dto.UserDto;

public class ApplicationResource {

	/*
	 * setup two caches: 1. Constants.CACHE_REGISTER_USER with entry (username,
	 * userDto); 2. Constants.CACHE_TOKEN_USERNAME with entry (token, username)
	 */
	public static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
			.withCache(Constants.CACHE_REGISTER_USER,
					CacheConfigurationBuilder
							.newCacheConfigurationBuilder(String.class, UserDto.class, ResourcePoolsBuilder.heap(100))
							.withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES))))
			.withCache(Constants.CACHE_TOKEN_USERNAME,
					CacheConfigurationBuilder
							.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100))
							.withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES))))
			.build();
}
