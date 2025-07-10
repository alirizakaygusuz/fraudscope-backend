package com.finscope.fraudscope.authentication.refreshtoken.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RefreshTokenCleanupScheduler {

	private final RefreshTokenRepository refreshTokenRepository;

	@Value("${jwt.refresh.token.cleanup.days}")
	private long cleanupThresholdInDays;

	
	
	@Scheduled(cron = "0 15 3 * * *") 
	@Transactional
	public void revokeExpiredRefreshTokens() {
	    LocalDateTime now = LocalDateTime.now();

	    List<RefreshToken> expiredTokens = refreshTokenRepository
	        .findAllByExpiryDateBeforeAndRevokedFalse(now);

	    expiredTokens.forEach(token -> {
	        token.setRevoked(true);
	        token.setRevokedAt(now);
	    });

	    refreshTokenRepository.saveAll(expiredTokens);

	    log.info("Revoked {} expired refresh tokens.", expiredTokens.size());
	}
	
	
	@Scheduled(cron = "0 15 3 * * *")
	@Transactional
	public void cleanUpExpiredTokens() {
		LocalDateTime threshold = LocalDateTime.now().minusDays(cleanupThresholdInDays);

		List<RefreshToken> tokensToDelete = refreshTokenRepository.findAllByRevokedTrueAndRevokedAtBefore(threshold);

		refreshTokenRepository.deleteAll(tokensToDelete);

		log.info("Cleaned up {} old revoked refresh tokens.", tokensToDelete.size());
	}

}
