package com.finscope.fraudscope.authentication.verification.token.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.helper.AuthTestDataFactory;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.authentication.verification.token.repository.VerificationTokenRepository;
import com.finscope.fraudscope.authentication.verification.token.service.impl.VerificationTokenServiceImpl;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.enums.ErrorType;
import com.finscope.fraudscope.common.notification.EmailService;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = "verification.token.expiration-minutes=240")
class VerificationTokenServiceImplTest {

	@Mock
	private VerificationTokenRepository verificationTokenRepository;
	@Mock
	private EmailService emailService;

	@Mock
	private AuthUserRepository authUserRepository;

	@InjectMocks
	private VerificationTokenServiceImpl verificationTokenServiceImpl;

	@DisplayName("createVerificationToken() should create verification token and return it")
	@Test
	void shouldCreateVerificationToken_whenAutUserRegister() {
		AuthUser authUser = AuthTestDataFactory.createValidAuthUser();
		TokenPurpose tokenPurpose = TokenPurpose.ACCOUNT_VERIFICATION;

		doNothing().when(emailService).sendAccountVerificationEmail(any(VerificationToken.class));

		when(verificationTokenRepository.save(any(VerificationToken.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		VerificationToken verificationToken = verificationTokenServiceImpl.createVerificationToken(authUser,
				tokenPurpose);

		assertThat(verificationToken).isNotNull();
		assertThat(verificationToken.getAuthUser()).isEqualTo(authUser);
		assertThat(verificationToken.getTokenPurpose()).isEqualTo(tokenPurpose);
		assertThat(verificationToken.getTokenValue()).isNotNull();

		verify(emailService).sendAccountVerificationEmail(verificationToken);
		verify(verificationTokenRepository).save(verificationToken);
		verifyNoInteractions(authUserRepository);

	}
	
	@DisplayName("verifyAccount() should verify account succesfully when token is valid(Active and Not Expired)")
	@Test
	void shouldVerifyAccountSuccessfully_whenTokenIsActiveAndNotExpired() {
		String token = "Valid-Verification-Token";
		VerificationToken verificationToken = AuthTestDataFactory.createValidVerificationToken();

		when(verificationTokenRepository.findByVerificationToken(token)).thenReturn(Optional.of(verificationToken));
		
		verificationTokenServiceImpl.verifyAccount(token);

		assertThat(verificationToken.getAuthUser().isEnabled()).isTrue(); 
	    assertThat(verificationToken.getTokenStatus()).isEqualTo(TokenStatus.USED);

	    verify(verificationTokenRepository).findByVerificationToken(token);            
	    verify(authUserRepository).save(verificationToken.getAuthUser());               
	    verify(verificationTokenRepository).save(verificationToken);        
		verifyNoInteractions(emailService);



	}

	@DisplayName("verifyAccount() should throw VERIFICATION_TOKEN_NOT_FOUND when token is not found in DB")
	@Test
	void shouldThrowVerificationTokenNotFound_whenTokenNotExists() {
		String token = "Invalid-Verification-Token";

		when(verificationTokenRepository.findByVerificationToken(token)).thenReturn(Optional.empty());

		assertThrows(BaseException.class, () -> verificationTokenServiceImpl.verifyAccount(token));

		verify(verificationTokenRepository).findByVerificationToken(token);
		verifyNoInteractions(emailService, authUserRepository);

	}

	@DisplayName("verifyAccount() should throw VERIFICATION_TOKEN_INVALID_OR_EXPIRED when token status is not ACTIVE")
	@Test
	void shouldThrowVerificationTokenInvalidOrExpired_whenVerificationTokenIsNotActive() {
		String token = "Valid-Verification-Token";
		VerificationToken verificationToken = AuthTestDataFactory.createBlockedVerificationToken();

		when(verificationTokenRepository.findByVerificationToken(token)).thenReturn(Optional.of(verificationToken));

		BaseException exception = assertThrows(BaseException.class,
				() -> verificationTokenServiceImpl.verifyAccount(token));

		assertThat(exception.getErrorMessage().getErrorType())
				.isEqualTo(ErrorType.VERIFICATION_TOKEN_INVALID_OR_EXPIRED);

		verify(verificationTokenRepository).findByVerificationToken(token);
		verifyNoInteractions(emailService, authUserRepository);

	}

	@DisplayName("verifyAccount() should throw VERIFICATION_TOKEN_INVALID_OR_EXPIRED when token is expired")
	@Test
	void shouldThrowVerificationTokenInvalidOrExpired_whenVerificationTokenIsAlreadyExpired() {
		String token = "Valid-Verification-Token";
		VerificationToken verificationToken = AuthTestDataFactory.createExpiredVerificationToken();

		when(verificationTokenRepository.findByVerificationToken(token)).thenReturn(Optional.of(verificationToken));

		BaseException exception = assertThrows(BaseException.class,
				() -> verificationTokenServiceImpl.verifyAccount(token));

		assertThat(exception.getErrorMessage().getErrorType())
				.isEqualTo(ErrorType.VERIFICATION_TOKEN_INVALID_OR_EXPIRED);

		verify(verificationTokenRepository).findByVerificationToken(token);
		verifyNoInteractions(emailService, authUserRepository);

	}

}
