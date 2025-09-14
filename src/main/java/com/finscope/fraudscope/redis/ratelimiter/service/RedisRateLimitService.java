package com.finscope.fraudscope.redis.ratelimiter.service;

import java.time.Duration;

public interface RedisRateLimitService {

	boolean isAllowed(String key , int limit , Duration window);
	
	void validateRateLimitOrThrow(String key, int limit , Duration window);
}
