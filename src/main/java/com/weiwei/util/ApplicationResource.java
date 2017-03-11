package com.weiwei.util;

import java.util.concurrent.TimeUnit;

import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.transactions.xa.configuration.XAStoreConfiguration;
import org.ehcache.transactions.xa.txmgr.btm.BitronixTransactionManagerLookup;
import org.ehcache.transactions.xa.txmgr.provider.LookupTransactionManagerProviderConfiguration;

import com.weiwei.common.Constants;
import com.weiwei.security.dto.UserDto;

//import bitronix.tm.BitronixTransactionManager;
//import bitronix.tm.TransactionManagerServices;

public class ApplicationResource {
	
//	public static BitronixTransactionManager transactionManager = TransactionManagerServices.getTransactionManager();

	/*
	 * setup two caches: 1. Constants.CACHE_REGISTER_USER with entry (username,
	 * userDto); 2. Constants.CACHE_TOKEN_USERNAME with entry (token, username)
	 */
//	public static CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
//			.using(new LookupTransactionManagerProviderConfiguration(BitronixTransactionManagerLookup.class))
//			.withCache(Constants.CACHE_REGISTER_USER,
//					CacheConfigurationBuilder
//							.newCacheConfigurationBuilder(String.class, UserDto.class, ResourcePoolsBuilder.heap(100))
//							.withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES)))
//							.add(new XAStoreConfiguration(Constants.CACHE_REGISTER_USER))
//							.build())
//			.withCache(Constants.CACHE_TOKEN_USERNAME,
//					CacheConfigurationBuilder
//							.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(100))
//							.withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES)))
//							.add(new XAStoreConfiguration(Constants.CACHE_TOKEN_USERNAME))
//							.build())
//			.build(true);
}
