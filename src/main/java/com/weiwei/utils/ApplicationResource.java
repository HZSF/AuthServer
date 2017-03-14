package com.weiwei.utils;

import java.util.concurrent.ForkJoinPool;

public class ApplicationResource {

	public static final ForkJoinPool THREAD_POOL = new ForkJoinPool(Runtime.getRuntime().availableProcessors() + 4);
	
}
