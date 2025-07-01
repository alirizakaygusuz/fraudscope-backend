package com.finscope.fraudscope.authentication.refreshtoken.scheduler;

import java.time.LocalDateTime;
import java.util.List;

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
	
	@Scheduled(cron = "0 15 3 * * *")
	@Transactional
	public void cleanUpExpiredTokens() {
		List<RefreshToken> expiredTokens = refreshTokenRepository
		        .findAllByExpiryDateBeforeAndRevokedFalse(LocalDateTime.now());

		    expiredTokens.forEach(token -> token.setRevoked(true));

		    refreshTokenRepository.saveAll(expiredTokens);
		    
		    
		    log.info("Marked {} expired refresh tokens as revoked.", expiredTokens.size());
	}

}
