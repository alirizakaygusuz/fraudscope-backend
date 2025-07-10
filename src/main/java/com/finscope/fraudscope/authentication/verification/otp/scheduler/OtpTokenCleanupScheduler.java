package com.finscope.fraudscope.authentication.verification.otp.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.otp.repository.OtpTokenRepository;
import com.finscope.fraudscope.common.exception.BaseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class OtpTokenCleanupScheduler {

	private final OtpTokenRepository otpTokenRepository;

	@Value("${otp.expiration-seconds}")
	private long otpExpirationSeconds;

	@Value("${otp.delete-after-days}")
	private long otpDeleteAfterDays;

	@Scheduled(fixedDelay = 180000)
	@Transactional(rollbackFor = BaseException.class)
	public void markExpiredOtpTokens() {
		LocalDateTime threshold = LocalDateTime.now().minusSeconds(otpExpirationSeconds);

		List<OtpToken> expireOtpTokens = otpTokenRepository.findAllByExpiryDateTimeBeforeAndTokenStatusNot(threshold,
				TokenStatus.BLOCKED);

		expireOtpTokens.forEach(token -> token.setTokenStatus(TokenStatus.EXPIRED));
		otpTokenRepository.saveAll(expireOtpTokens);

		log.info("Marked {} OTP tokens as EXPIRED.", expireOtpTokens.size());
	}

	@Scheduled(cron = "0 15 3 28 * *")
	@Transactional(rollbackFor = BaseException.class)
	public void cleanUpExpiredOtpToken() {
		LocalDateTime threshold = LocalDateTime.now().minusDays(otpDeleteAfterDays);

		List<OtpToken> expireOtpTokens = otpTokenRepository.findAllByExpiryDateTimeBeforeAndTokenStatusNot(threshold,
				TokenStatus.BLOCKED);

		otpTokenRepository.deleteAll(expireOtpTokens);

		log.info("Deleted {} OTP tokens as EXPIRED.", expireOtpTokens.size());

	}
}
