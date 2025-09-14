package com.finscope.fraudscope.redis.ratelimiter.policy;

import java.time.Duration;

public enum RateLimitPolicies {

	LOGIN(new RateLimitPolicy(5, Duration.ofMinutes(2))),
	VERIFY_OTP(new RateLimitPolicy(5, Duration.ofMinutes(5))),
	REGISTER(new RateLimitPolicy(10, Duration.ofHours(1)));
	
	
	private final RateLimitPolicy rateLimitPolicy;
	
	RateLimitPolicies(RateLimitPolicy rateLimitPolicy){
		this.rateLimitPolicy = rateLimitPolicy;
	}
	
	public RateLimitPolicy getPolicy() {
		return rateLimitPolicy;
	}
	
}
