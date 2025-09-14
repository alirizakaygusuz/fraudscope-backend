package com.finscope.fraudscope.redis.ratelimiter.policy;

import java.time.Duration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RateLimitPolicy {
	
	private final int limit;
	private final Duration window;

}
