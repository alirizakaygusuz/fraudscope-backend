package com.finscope.fraudscope.authentication.verification.token.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.authentication.verification.token.repository.VerificationTokenRepository;
import com.finscope.fraudscope.authentication.verification.token.service.VerificationTokenService;
import com.finscope.fraudscope.common.notification.EmailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VerificationTokenServiceImpl implements VerificationTokenService {

	@Value("${verification.token.expiration-minutes}")
	private int expiration;

	private final VerificationTokenRepository verificationTokenRepository;

	private final EmailService emailService;

	private final AuthUserRepository authUserRepository;

	@Override
	public VerificationToken createVerificationToken(AuthUser authUser, TokenPurpose tokenPurpose) {
		VerificationToken token = new VerificationToken();
		token.setVerificationToken(UUID.randomUUID().toString());
		token.setAuthUser(authUser);
		token.setExpiryDateTime(LocalDateTime.now().plusMinutes(expiration));
		token.setTokenPurpose(tokenPurpose);
		token.setTokenStatus(TokenStatus.ACTIVE);

		return verificationTokenRepository.save(token);
	}

	@Override
	public void sendVerificationEmail(VerificationToken verificationToken) {

		emailService.sendVerificationEmail(verificationToken);

	}

	@Override
	public void verifyAccount(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByVerificationToken(token)
				.orElseThrow(() -> new RuntimeException("Token not found or invalid."));

		if (verificationToken.getTokenStatus() == TokenStatus.ACTIVE
				&& verificationToken.getExpiryDateTime().isAfter(LocalDateTime.now())) {

			AuthUser user = verificationToken.getAuthUser();
			user.setEnabled(true); // Activate user!
			authUserRepository.save(user);

			verificationToken.setTokenStatus(TokenStatus.USED);
			verificationTokenRepository.save(verificationToken);

		} else {
			throw new RuntimeException("Token has expired or already been used.");
		}
	}

}
