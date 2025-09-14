package com.finscope.fraudscope.redis.ratelimiter.exception;

import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

public class RateLimitExceededException extends BaseException {

	public RateLimitExceededException() {
		super(new ErrorMessage(ErrorType.REDIS_RATE_LIMIT_EXCEEDED));
	}
	
	public RateLimitExceededException(String message) {
        super(new ErrorMessage(ErrorType.REDIS_RATE_LIMIT_EXCEEDED, message));
    }

}
