package com.weiwei.security;

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
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import com.weiwei.common.Constants;
import com.weiwei.security.dto.UserDto;

@SpringBootApplication
@EnableCaching
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	public CacheManager getCacheManager() {
		return CacheManagerBuilder.newCacheManagerBuilder()
				.using(new LookupTransactionManagerProviderConfiguration(BitronixTransactionManagerLookup.class))
				.withCache(Constants.CACHE_REGISTER_USER, CacheConfigurationBuilder
						.newCacheConfigurationBuilder(String.class, UserDto.class, ResourcePoolsBuilder.heap(500))
						.withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES)))
						.add(new XAStoreConfiguration(Constants.CACHE_REGISTER_USER)).build())
				.withCache(Constants.CACHE_TOKEN_USERNAME, CacheConfigurationBuilder
						.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(500))
						.withExpiry(Expirations.timeToLiveExpiration(Duration.of(1, TimeUnit.MINUTES)))
						.add(new XAStoreConfiguration(Constants.CACHE_TOKEN_USERNAME)).build())
				.build(true);
	}

}
