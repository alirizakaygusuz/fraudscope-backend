package com.finscope.fraudscope.authentication.verification.token.service.impl;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.authentication.verification.token.repository.VerificationTokenRepository;
import com.finscope.fraudscope.authentication.verification.token.service.VerificationTokenService;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;
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
	@Transactional(rollbackFor = BaseException.class)
	public VerificationToken createVerificationToken(AuthUser authUser, TokenPurpose tokenPurpose) {

		VerificationToken verificationToken = VerificationToken.builder()
				.verificationToken(UUID.randomUUID().toString()).authUser(authUser)
				.expiryDateTime(LocalDateTime.now().plusMinutes(expiration)).tokenPurpose(tokenPurpose)
				.tokenStatus(TokenStatus.ACTIVE).build();

		emailService.sendAccountVerificationEmail(verificationToken);

		return verificationTokenRepository.save(verificationToken);
	}

	@Override
	@Transactional(rollbackFor = BaseException.class)
	public void verifyAccount(String token) {
		VerificationToken verificationToken = verificationTokenRepository.findByVerificationToken(token)
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.VERIFICATION_TOKEN_NOT_FOUND)));

		if (verificationToken.getTokenStatus() == TokenStatus.ACTIVE
				&& verificationToken.getExpiryDateTime().isAfter(LocalDateTime.now())) {

			AuthUser user = verificationToken.getAuthUser();
			user.setEnabled(true); // Activate user!
			authUserRepository.save(user);

			verificationToken.setTokenStatus(TokenStatus.USED);
			verificationTokenRepository.save(verificationToken);

		} else {
			throw new BaseException(new ErrorMessage(ErrorType.VERIFICATION_TOKEN_INVALID_OR_EXPIRED));
		}
	}

}
