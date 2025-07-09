package com.finscope.fraudscope.authentication.verification.otp.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.otp.repository.OtpTokenRepository;
import com.finscope.fraudscope.authentication.verification.otp.service.OtpTokenService;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.notification.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OtpTokenServiceImpl implements OtpTokenService {

	private final OtpTokenRepository otpTokenRepository;
	
	private final EmailService emailService;


	@Value("${otp.expiration-seconds}")
	private long otpExpirationSeconds;

	@Override
	@Transactional(rollbackFor = BaseException.class)
	public OtpToken createOtpToken(AuthUser authUser,LoginRequest loginRequest,TokenPurpose tokenPurpose) {
		OtpToken otpToken = buildOtpToken(loginRequest,tokenPurpose);
		otpToken.setAuthUser(authUser);

		emailService.sendOtpCodeEmail(otpToken);
		
		
		return otpTokenRepository.save(otpToken);
	}
	
	

	private OtpToken buildOtpToken(LoginRequest loginRequest , TokenPurpose tokenPurpose) {

		String otpCode = generateOtpCode();
		String otpTokenVerification = UUID.randomUUID().toString();
	
		return OtpToken.builder()
				.otpCode(otpCode)
				.otpVerificationToken(otpTokenVerification)
				.tokenPurpose(tokenPurpose)
				.tokenStatus(TokenStatus.ACTIVE)
				.expiryDateTime(LocalDateTime.now().plusSeconds(otpExpirationSeconds))
				.attemptCount(0)
				.ipAddress(loginRequest.getIpAddress())
				.userAgent(loginRequest.getUserAgent())
				.build();		
	}

	private String generateOtpCode() {
		SecureRandom secureRandom = new SecureRandom();
		int otpCode = secureRandom.nextInt(900_000) + 100_000;

		return String.format("%06d", otpCode);
	}

	
	
	

}
