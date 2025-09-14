package com.finscope.fraudscope.redis.ratelimiter.exception;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.kafka.dto.KafkaMailPayload;
import com.finscope.fraudscope.kafka.producer.MailProducerService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class RateLimitExceptionHandler {

	private final AuthUserRepository authUserRepository;
	private final MailProducerService mailProducerService;

	@Transactional
	public void handleLoginRateLimitExceeded(String emailOrUsername) {

		Optional<AuthUser> authUserOpt = authUserRepository.findByUsernameOrEmail(emailOrUsername);

		if (authUserOpt.isPresent()) {
			AuthUser authUser = authUserOpt.get();

			if (shouldSendRateLimitWarningMail(authUser)) {
				KafkaMailPayload payload = KafkaMailPayload.builder().toEmail(authUser.getEmail())
						.subject("Rate Limit Exceeded")
						.content("You have exceeded the allowed login attempts. Please try again in 15 minutes.")
						.build();
				mailProducerService.sendRateLimitWarningMail(payload);
				log.warn("[RateLimit] Warning mail sent to {}", authUser.getEmail());
				
				authUser.setLastRateLimitWarnSentAt(LocalDateTime.now());
				authUserRepository.save(authUser);
			}

		} else {
			log.warn("[RateLimit] Login rate limit exceeded for unknown user/email: {}", emailOrUsername);
		}

	}

	private boolean shouldSendRateLimitWarningMail(AuthUser authUser) {
		LocalDateTime lastSenDateTime = authUser.getLastRateLimitWarnSentAt();
		return lastSenDateTime == null||  Duration.between(lastSenDateTime, LocalDateTime.now()).toMinutes() >= 15;
	}
	
}
