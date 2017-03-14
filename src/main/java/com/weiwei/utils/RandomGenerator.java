package com.weiwei.utils;

import java.util.UUID;

public class RandomGenerator {

	public static String generateToken() {
		return UUID.randomUUID().toString().toUpperCase();
	}

}
