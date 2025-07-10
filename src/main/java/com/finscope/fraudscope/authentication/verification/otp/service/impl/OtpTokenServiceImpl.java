package com.finscope.fraudscope.authentication.verification.otp.service.impl;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.jwt.JWTService;
import com.finscope.fraudscope.authentication.mapper.AuthMapper;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.service.RefreshTokenService;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.otp.dto.OtpTokenRequest;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.otp.repository.OtpTokenRepository;
import com.finscope.fraudscope.authentication.verification.otp.service.OtpTokenService;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;
import com.finscope.fraudscope.common.notification.EmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OtpTokenServiceImpl implements OtpTokenService {

	private final OtpTokenRepository otpTokenRepository;
	
	private final RefreshTokenService refreshTokenService; 
	
	private final EmailService emailService;

	private final JWTService jwtService;

	private final AuthMapper authMapper;

	 @Qualifier("newTransactionTemplate")
	 private final TransactionTemplate newTransactionTemplate;
	
	@Value("${otp.expiration-seconds}")
	private long otpExpirationSeconds;

	@Value("${otp.max-attempt}")
	private int maxAttempt;

	@Override
	@Transactional(rollbackFor = {BaseException.class, Exception.class})
	public OtpToken createOtpToken(AuthUser authUser, LoginRequest loginRequest, TokenPurpose tokenPurpose) {
		OtpToken otpToken = buildOtpToken(loginRequest, tokenPurpose);
		otpToken.setAuthUser(authUser);

		emailService.sendOtpCodeEmail(otpToken);

		return otpTokenRepository.save(otpToken);
	}

	private OtpToken buildOtpToken(LoginRequest loginRequest, TokenPurpose tokenPurpose) {

		String otpCode = generateOtpCode();
		String otpTokenVerification = UUID.randomUUID().toString();

		return OtpToken.builder()
				.otpCode(otpCode)
				.otpVerificationToken(otpTokenVerification)
				.tokenPurpose(tokenPurpose)
				.tokenStatus(TokenStatus.ACTIVE)
				.expiryDateTime(LocalDateTime.now()
						.plusSeconds(otpExpirationSeconds))
				.attemptCount(0).ipAddress(loginRequest.getIpAddress())
				.userAgent(loginRequest.getUserAgent())
				.build();
	}

	private String generateOtpCode() {
		SecureRandom secureRandom = new SecureRandom();
		int otpCode = secureRandom.nextInt(900_000) + 100_000;

		return String.format("%06d", otpCode);
	}

	@Override
	@Transactional(rollbackFor = BaseException.class)
	public LoginResponse completeLoginWithOtp(OtpTokenRequest request) {

		
		OtpToken otpToken = otpTokenRepository
				.findByOtpVerificationTokenAndTokenStatus(request.getOtpVerificationToken(), TokenStatus.ACTIVE)
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.OTP_TOKEN_INVALID_OR_EXPIRED)));

		if (otpToken.getAttemptCount() >= maxAttempt) {
			blockedOtptoken(otpToken);
			throw new BaseException(new ErrorMessage(ErrorType.OTP_TOKEN_IS_BLOCKED));
		}

		if (!otpToken.getOtpCode().equals(request.getOtpCode())) {
			increaseAttemptCount(otpToken);	
			throw new BaseException(new ErrorMessage(ErrorType.OTP_TOKEN_INVALID_OR_EXPIRED));

		}
		

		otpToken.setTokenStatus(TokenStatus.USED);
		otpTokenRepository.save(otpToken);

		AuthUser authUser = otpToken.getAuthUser();

		RefreshToken refreshToken = refreshTokenService.createAndSave(authUser, otpToken.getIpAddress(),
				otpToken.getUserAgent());
		

		String accessToken = jwtService.generateToken(authUser);

		return authMapper.toLoginResponse(authUser, accessToken, refreshToken.getToken());

	}
	
	private void blockedOtptoken(OtpToken otpToken) {
		  try {
			  newTransactionTemplate.executeWithoutResult(status -> {
		    		otpToken.setTokenStatus(TokenStatus.BLOCKED);
					otpTokenRepository.save(otpToken);
		        });
		    } catch (Exception e) {
		        throw new BaseException(new ErrorMessage(ErrorType.OTP_TOKEN_UPDATE_FAILED));
		    }
	}
	
	private void increaseAttemptCount(OtpToken otpToken) {
	    try {
	    	newTransactionTemplate.executeWithoutResult(status -> {
	            otpToken.setAttemptCount(otpToken.getAttemptCount() + 1);
	            otpTokenRepository.save(otpToken);
	        });
	    } catch (Exception e) {
	        throw new BaseException(new ErrorMessage(ErrorType.OTP_TOKEN_UPDATE_FAILED));
	    }
	}



}
