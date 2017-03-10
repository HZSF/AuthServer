package com.weiwei.security.listener;

import org.ehcache.event.CacheEvent;
import org.ehcache.event.CacheEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EHCacheEventLogger implements CacheEventListener<Object, Object> {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void onEvent(CacheEvent<? extends Object, ? extends Object> event) {
		logger.info("Event: " + event.getType() + " Key: " + event.getKey() + " old value: " + event.getOldValue()
				+ " new value: " + event.getNewValue());
	}

}
