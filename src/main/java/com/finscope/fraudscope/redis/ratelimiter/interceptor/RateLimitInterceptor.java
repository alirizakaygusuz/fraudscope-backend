package com.finscope.fraudscope.redis.ratelimiter.interceptor;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.verification.otp.dto.OtpTokenRequest;
import com.finscope.fraudscope.common.web.wrapper.MultiReadHttpServletRequest;
import com.finscope.fraudscope.redis.ratelimiter.key.RateLimitKeyGenerator;
import com.finscope.fraudscope.redis.ratelimiter.policy.RateLimitPolicies;
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

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String path = request.getRequestURI();
		log.info("[RateLimitInterceptor] -> Incoming request to {}", path);

		if (path.endsWith("/register")) {

			var policy = RateLimitPolicies.REGISTER.getPolicy();
			String key = RateLimitKeyGenerator.forRegister(request);
			redisRateLimitService.validateRateLimitOrThrow(key, policy.getLimit(), policy.getWindow());

		} else if (path.endsWith("/login")) {

			String body = extractRequestBody(request);
			
			log.info("RateLimitInterceptor [LOGIN] body: {}", body);
			checkAndApplyRateLimitPolicy(body, RateLimitPolicies.LOGIN);

		} else if (path.endsWith("/verify-otp")) {
			String body = extractRequestBody(request);
			
			log.info("RateLimitInterceptor [VERIFY-OTP] body: {}", body);
			checkAndApplyRateLimitPolicy(body, RateLimitPolicies.VERIFY_OTP);
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

	private void checkAndApplyRateLimitPolicy(String body, RateLimitPolicies policyType)
			throws JsonProcessingException {

		log.info("RateLimitInterceptor [checkAndApplyRateLimitPolicy Method] body: {} | policy: {}", body,
				policyType.getPolicy());

		var policy = policyType.getPolicy();
		String key = null;

		if (policyType == RateLimitPolicies.LOGIN) {

			LoginRequest loginRequest = objectMapper.readValue(body, LoginRequest.class);
			String emailOrUsername = loginRequest.getEmailOrUsername();
			key = RateLimitKeyGenerator.forLogin(emailOrUsername);

		} else if (policyType == RateLimitPolicies.VERIFY_OTP) {

			OtpTokenRequest otpReq = objectMapper.readValue(body, OtpTokenRequest.class);
			String token = otpReq.getOtpVerificationToken();

			key = RateLimitKeyGenerator.forOtp(token);

		} else {
			throw new IllegalArgumentException("Unsupported policy type: " + policyType);
		}

		redisRateLimitService.validateRateLimitOrThrow(key, policy.getLimit(), policy.getWindow());
	}

}
