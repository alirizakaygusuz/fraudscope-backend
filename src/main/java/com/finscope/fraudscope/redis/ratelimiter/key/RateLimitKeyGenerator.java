package com.finscope.fraudscope.redis.ratelimiter.key;

import com.finscope.fraudscope.common.util.IpUtils;

import jakarta.servlet.http.HttpServletRequest;

public class RateLimitKeyGenerator {
	
	private RateLimitKeyGenerator() {
		
	}

	private static final String PREFIX = "rate-limit";

	public static String forLogin(String emailOrUsername) {
		return String.format("%s:login:%s", PREFIX, formatIdentifier(emailOrUsername));
	}

	public static String forOtp(String otpVerificationToken) {
		return String.format("%s:verify-otp:%s", PREFIX, otpVerificationToken);
	}

	public static String forRegister(HttpServletRequest request) {
		String ip = IpUtils.getClientIp(request);
		return String.format("%s:register:%s", PREFIX, ip);
	}
	
	public static String forRefreshToken(String refreshToken) {
		return String.format("%s:refresh-token:%s", PREFIX , refreshToken);
	}

	private static String formatIdentifier(String identifier) {

		if (identifier == null || identifier.isBlank()) {
			return "unknown";
		}

		return identifier.trim().toLowerCase();
	}

}
