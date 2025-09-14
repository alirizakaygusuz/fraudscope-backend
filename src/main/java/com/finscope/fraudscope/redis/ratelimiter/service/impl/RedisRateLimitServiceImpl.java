package com.finscope.fraudscope.redis.ratelimiter.service.impl;

import java.time.Duration;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.redis.ratelimiter.exception.RateLimitExceededException;
import com.finscope.fraudscope.redis.ratelimiter.service.RedisRateLimitService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RedisRateLimitServiceImpl implements RedisRateLimitService {

	private final StringRedisTemplate redisTemplate;
	
	//For key - window & limit control 
	//increase Counter  -  TTL
	@Override
	public boolean isAllowed(String key, int limit, Duration window) {
		Long count = redisTemplate.opsForValue().increment(key);
		
		if(count == null) {
			return true;
		}
		
		if(count == 1L) {
			redisTemplate.expire(key, window);
		}
		
		return count <= limit;
		
	}

	@Override
	public void validateRateLimitOrThrow(String key, int limit, Duration window) {
		boolean allowed = isAllowed(key, limit, window);
		
		if(!allowed) {
			throw new RateLimitExceededException(key);
		}
	}

}
