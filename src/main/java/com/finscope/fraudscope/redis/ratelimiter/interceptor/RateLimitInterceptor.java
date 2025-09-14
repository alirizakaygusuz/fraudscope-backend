package com.finscope.fraudscope.redis.ratelimiter.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.verification.otp.dto.OtpTokenRequest;
import com.finscope.fraudscope.common.web.wrapper.MultiReadHttpServletRequest;
import com.finscope.fraudscope.redis.ratelimiter.exception.RateLimitExceededException;
import com.finscope.fraudscope.redis.ratelimiter.exception.RateLimitExceptionHandler;
import com.finscope.fraudscope.redis.ratelimiter.key.RateLimitKeyGenerator;
import com.finscope.fraudscope.redis.ratelimiter.policy.RateLimitPolicies;
import com.finscope.fraudscope.redis.ratelimiter.policy.RateLimitPolicy;
import com.finscope.fraudscope.redis.ratelimiter.service.RedisRateLimitService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitInterceptor implements HandlerInterceptor {

	private final RedisRateLimitService redisRateLimitService;
	private final ObjectMapper objectMapper;
	private final RateLimitExceptionHandler rateLimitExceptionHandler;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getRequestURI();
		log.info("[RateLimitInterceptor] -> Incoming request to {}", path);

		if (path.endsWith("/register")) {
			handleRegisterPolicy(request);
		} else if (path.endsWith("/login")) {
			handleLoginPolicy(request);

		} else if (path.endsWith("/verify-otp")) {
			handleOtpPolicy(request);
		}

		return true;
	}


	private String extractRequestBody(HttpServletRequest request) throws IOException {
		log.info("[RateLimitInterceptor] -> Incoming request to {}", request.getRequestURI());

		Object cached = request.getAttribute("cachedAuthRequest");

		if (cached instanceof MultiReadHttpServletRequest wrapper) {
		    String body = new String(wrapper.getCachedBody(), StandardCharsets.UTF_8);
			log.info("RateLimitInterceptor [ExtractRequestBody Method] body: {}", body);
			return body;

		}
		return "";
	}

	
	
	private void handleRegisterPolicy(HttpServletRequest request) {
		RateLimitPolicy policy = RateLimitPolicies.REGISTER.getPolicy();
		String key = RateLimitKeyGenerator.forRegister(request);
		
		log.info("[RateLimitInterceptor-handleRegisterPolicy] ->  key to {}", key);

		
		redisRateLimitService.validateRateLimitOrThrow(key, policy.getLimit(), policy.getWindow());
	}
	
	private void handleLoginPolicy(HttpServletRequest request) throws IOException {
		String body = extractRequestBody(request);
		LoginRequest loginRequest = objectMapper.readValue(body, LoginRequest.class);
		String emailOrUsername = loginRequest.getEmailOrUsername();
		String key = RateLimitKeyGenerator.forLogin(emailOrUsername);
		RateLimitPolicy policy = RateLimitPolicies.LOGIN.getPolicy();

		log.info("[RateLimitInterceptor-handleLoginPolicy] ->  Body to {}", body);

		try {
			redisRateLimitService.validateRateLimitOrThrow(key, policy.getLimit(), policy.getWindow());
		} catch (RateLimitExceededException e) {
			rateLimitExceptionHandler.handleLoginRateLimitExceeded(emailOrUsername);
			throw e;
		}
	}
	
	
	private void handleOtpPolicy(HttpServletRequest request) throws IOException {
		String body = extractRequestBody(request);
		OtpTokenRequest otpReq = objectMapper.readValue(body, OtpTokenRequest.class);
		String token = otpReq.getOtpVerificationToken();
		String key = RateLimitKeyGenerator.forOtp(token);
		RateLimitPolicy policy = RateLimitPolicies.VERIFY_OTP.getPolicy();
		
		log.info("[RateLimitInterceptor-handleOtpPolicy] ->  Body to {}", body);

		redisRateLimitService.validateRateLimitOrThrow(key, policy.getLimit(), policy.getWindow());
	}

}
