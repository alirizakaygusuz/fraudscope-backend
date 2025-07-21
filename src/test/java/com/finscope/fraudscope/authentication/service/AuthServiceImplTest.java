package com.finscope.fraudscope.authentication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.OtpRequiredResponse;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.helper.AuthTestDataFactory;
import com.finscope.fraudscope.authentication.jwt.JWTService;
import com.finscope.fraudscope.authentication.mapper.AuthMapper;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.service.RefreshTokenService;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authentication.service.impl.AuthServiceImpl;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.otp.service.OtpTokenService;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.authentication.verification.token.service.VerificationTokenService;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.role.repository.RoleRepository;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

	@Mock
	private AuthUserRepository authUserRepository;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private RefreshTokenService refreshTokenService;

	@Mock
	private JWTService jwtService;

	@Mock
	private VerificationTokenService verificationTokenService;

	@Mock
	private OtpTokenService otpTokenService;

	@Mock
	private AuthMapper authMapper;

	@Mock
	private BCryptPasswordEncoder passwordEncoder;

	@InjectMocks
	private AuthServiceImpl authServiceImpl;

	@DisplayName("register() should create user successfully when given valid input")
	@Test
	void shouldUserRegisterSuccesfully_whenValidInput() {
		RegisterRequest request = AuthTestDataFactory.createValidRegisterRequest();

		when(authUserRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
		when(authUserRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());
		when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

		Role role = AuthTestDataFactory.createValidUserRole();
		when(roleRepository.findByName("USER")).thenReturn(Optional.of(role));

		AuthUser authUser = AuthTestDataFactory.createValidAuthUser();
		ReflectionTestUtils.setField(authUser, "id", 1L);

		when(authUserRepository.save(Mockito.any(AuthUser.class))).thenReturn(authUser);

		TokenPurpose tokenPurpose = TokenPurpose.ACCOUNT_VERIFICATION;

		VerificationToken verificationToken = AuthTestDataFactory.createValidVerificationToken();
		when(verificationTokenService.createVerificationToken(authUser, tokenPurpose)).thenReturn(verificationToken);

		RegisterResponse expectedResponse = new RegisterResponse();
		expectedResponse.setUsername(authUser.getUsername());
		expectedResponse.setEmail(authUser.getEmail());
		expectedResponse.setMessage("Please verify your email!!!");

		when(authMapper.toRegisterResponse(Mockito.any(AuthUser.class))).thenReturn(expectedResponse);

		RegisterResponse actualResponse = authServiceImpl.register(request);

		assertThat(actualResponse.getUsername()).isEqualTo(expectedResponse.getUsername());
		assertThat(actualResponse.getEmail()).isEqualTo(expectedResponse.getEmail());
		assertThat(actualResponse.getMessage()).isEqualTo("Please verify your email!!!");

		verify(authUserRepository).findByUsername(request.getUsername());
		verify(authUserRepository).findByEmail(request.getEmail());
		verify(roleRepository).findByName("USER");
		verify(authUserRepository).save(Mockito.any(AuthUser.class));
		verify(verificationTokenService).createVerificationToken(authUser, tokenPurpose);
		verify(authMapper).toRegisterResponse(authUser);
		verifyNoInteractions(refreshTokenService, jwtService, otpTokenService);

	}

	@DisplayName("register() should fail with USERNAME_ALREADY_EXISTS if username is already in use")
	@Test
	void shouldThrowUsernameAlreadyExists_whenUsernameAlreadyExists() {

		RegisterRequest request = AuthTestDataFactory.createValidRegisterRequest();

		when(authUserRepository.findByUsername(request.getUsername())).thenReturn(Optional.of(new AuthUser()));

		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.register(request));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.USERNAME_ALREADY_EXISTS);

		verify(authUserRepository).findByUsername(request.getUsername());
		verifyNoInteractions(roleRepository, refreshTokenService, jwtService, verificationTokenService, otpTokenService,
				authMapper);
	}

	@DisplayName("register() should fail with EMAIL_ALREADY_EXISTS if email is already in use")
	@Test
	void shouldThrowEmailAlreadyExists_whenEmailAlreadyExists() {
		RegisterRequest request = AuthTestDataFactory.createValidRegisterRequest();

		when(authUserRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new AuthUser()));

		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.register(request));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.EMAIL_ALREADY_EXISTS);

		verify(authUserRepository).findByEmail(request.getEmail());
		verifyNoInteractions(roleRepository, refreshTokenService, jwtService, verificationTokenService, otpTokenService,
				authMapper);
	}

	@DisplayName("register() should throw ROLE_NOT_FOUND when default role does not exist")
	@Test
	void shouldThrowRoleNotFoundException_whenDefaultRoleNotExists() {
		RegisterRequest request = AuthTestDataFactory.createValidRegisterRequest();
		when(authUserRepository.findByUsername(request.getUsername())).thenReturn(Optional.empty());
		when(authUserRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

		when(passwordEncoder.encode(request.getPassword())).thenReturn("encoded-password");

		when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.register(request));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.ROLE_NOT_FOUND);

		verify(authUserRepository).findByUsername(request.getUsername());
		verify(authUserRepository).findByEmail(request.getEmail());
		verify(roleRepository).findByName("USER");
		verifyNoInteractions(refreshTokenService, jwtService, verificationTokenService, otpTokenService, authMapper);
	}

	@DisplayName("login() should return OTP response when 2FA is enabled and credentials are valid")
	@Test
	void shouldUserLoginSuccessfully_whenUserIsVerifiedAnd2faEnabled() {
		LoginRequest loginRequest = AuthTestDataFactory.createValidLoginRequest();
		AuthUser authUser = AuthTestDataFactory.createValidAuthUserWithEnabled();

		when(authUserRepository.findByUsernameOrEmail(loginRequest.getEmailOrUsername()))
				.thenReturn(Optional.of(authUser));

		when(passwordEncoder.matches(loginRequest.getPassword(), authUser.getPassword())).thenReturn(true);

		OtpToken otpToken = OtpToken.builder().otpVerificationToken("mock-otp-token").authUser(authUser).build();
		TokenPurpose tokenPurpose = TokenPurpose.TWO_FACTOR_AUTH;

		when(otpTokenService.createOtpToken(authUser, loginRequest, tokenPurpose)).thenReturn(otpToken);

		Object result = authServiceImpl.login(loginRequest);
		assertThat(result).isInstanceOf(OtpRequiredResponse.class);

		OtpRequiredResponse otpRequiredResponse = (OtpRequiredResponse) result;
		assertThat(otpRequiredResponse.getOtpVerificationToken()).isEqualTo("mock-otp-token");
		assertThat(otpRequiredResponse.getMessage()).isEqualTo("OTP CODE has been sent to your email.");

		verify(authUserRepository).findByUsernameOrEmail(loginRequest.getEmailOrUsername());
		verify(passwordEncoder).matches(loginRequest.getPassword(), authUser.getPassword());
		verify(otpTokenService).createOtpToken(authUser, loginRequest, tokenPurpose);

		verifyNoInteractions(roleRepository, refreshTokenService, jwtService, verificationTokenService, authMapper);

	}

	@DisplayName("login() should return Login response when 2FA is disabled and credentials are valid")
	@Test
	void shouldUserLoginSuccessfully_whenUserIsVerifiedAnd2faDisabled() {
		LoginRequest loginRequest = AuthTestDataFactory.createValidLoginRequest();
		AuthUser authUser = AuthTestDataFactory.createValidAuthUserWithEnabledAndDisable2FA();
		

		when(authUserRepository.findByUsernameOrEmail(loginRequest.getEmailOrUsername()))
				.thenReturn(Optional.of(authUser));
		

		when(passwordEncoder.matches(loginRequest.getPassword(), authUser.getPassword())).thenReturn(true);

		RefreshToken refreshToken = AuthTestDataFactory.createValidRefreshToken();
		when(refreshTokenService.createAndSave(authUser, loginRequest.getIpAddress(), loginRequest.getUserAgent()))
				.thenReturn(refreshToken);

		String accesToken = "Valid-Access-Token";
		when(jwtService.generateToken(authUser)).thenReturn(accesToken);

		LoginResponse expectedLoginResponse = AuthTestDataFactory.createValidLoginResponse();
		when(authMapper.toLoginResponse(authUser, accesToken, refreshToken.getToken()))
				.thenReturn(expectedLoginResponse);

		Object result = authServiceImpl.login(loginRequest);

		assertThat(result).isInstanceOf(LoginResponse.class);
		LoginResponse actualLoginResponse = (LoginResponse) result;

		assertThat(actualLoginResponse.getUsername()).isEqualTo(expectedLoginResponse.getUsername());
		assertThat(actualLoginResponse.getEmail()).isEqualTo(expectedLoginResponse.getEmail());
		assertThat(actualLoginResponse.getAccessToken()).isEqualTo(expectedLoginResponse.getAccessToken());
		assertThat(actualLoginResponse.getRefreshToken()).isEqualTo(expectedLoginResponse.getRefreshToken());

		verify(authUserRepository).findByUsernameOrEmail(loginRequest.getEmailOrUsername());
		verify(passwordEncoder).matches(loginRequest.getPassword(), authUser.getPassword());
		verify(refreshTokenService).createAndSave(authUser, loginRequest.getIpAddress(), loginRequest.getUserAgent());
		verify(jwtService).generateToken(authUser);
		verify(authMapper).toLoginResponse(authUser, accesToken, refreshToken.getToken());

		verifyNoInteractions(roleRepository, verificationTokenService, otpTokenService);

	}

	@DisplayName("login() should throw USERNAME_OR_EMAIL_NOT_FOUND when no account exists for given username or email")
	@Test
	void shouldThrowUsernameOrEmailNotFound_whenUsernameAndEmailDoNotHaveAnyAccount() {
		LoginRequest loginRequest = AuthTestDataFactory.createValidLoginRequest();

		when(authUserRepository.findByUsernameOrEmail(loginRequest.getEmailOrUsername())).thenReturn(Optional.empty());

		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.login(loginRequest));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.USERNAME_OR_EMAIL_NOT_FOUND);

		verify(authUserRepository).findByUsernameOrEmail(loginRequest.getEmailOrUsername());
		verifyNoInteractions(roleRepository, refreshTokenService, jwtService, verificationTokenService, otpTokenService,
				authMapper);

	}
	

	@DisplayName("login() should throw USER_NOT_FOUND when AuthUser marked soft deleted")
	@Test
	void shouldThrowUserNotFound_whenAuthUserMarkedSoftdeleted() {
		LoginRequest loginRequest = AuthTestDataFactory.createValidLoginRequest();
		AuthUser authUser = AuthTestDataFactory.createValidAuthUser();
		authUser.setDeleted(true);
		
		when(authUserRepository.findByUsernameOrEmail(loginRequest.getEmailOrUsername()))
				.thenReturn(Optional.of(authUser));


		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.login(loginRequest));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.USER_NOT_FOUND);

		
		verify(authUserRepository).findByUsernameOrEmail(loginRequest.getEmailOrUsername());
		verifyNoInteractions(roleRepository,passwordEncoder,refreshTokenService, jwtService, verificationTokenService, otpTokenService,
				authMapper);

	}


	@DisplayName("login() should throw INVALID_PASSWORD when AuthUser exists but password is incorrect")
	@Test
	void shouldThrowInvalidPassword_whenAuthUserExistsAndPasswordNotMatch() {
		LoginRequest loginRequest = AuthTestDataFactory.createValidLoginRequest();
		AuthUser authUser = AuthTestDataFactory.createValidAuthUser();

		when(authUserRepository.findByUsernameOrEmail(loginRequest.getEmailOrUsername()))
				.thenReturn(Optional.of(authUser));

		when(passwordEncoder.matches(loginRequest.getPassword(), authUser.getPassword())).thenReturn(false);

		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.login(loginRequest));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.INVALID_PASSWORD);

		
		verify(authUserRepository).findByUsernameOrEmail(loginRequest.getEmailOrUsername());
		verify(passwordEncoder).matches(loginRequest.getPassword(), authUser.getPassword());
		verifyNoInteractions(roleRepository, refreshTokenService, jwtService, verificationTokenService, otpTokenService,
				authMapper);

	}

	@DisplayName("login() should throw ACCOUNT_NOT_VERIFIED when AuthUser exists but not verified account via email verificaiton")
	@Test
	void shouldThrowAccountNotVerified_whenAuthUserExistsAndAccountNotVerifiedViaEmail() {
		LoginRequest loginRequest = AuthTestDataFactory.createValidLoginRequest();
		AuthUser authUser = AuthTestDataFactory.createValidAuthUser();

		when(authUserRepository.findByUsernameOrEmail(loginRequest.getEmailOrUsername()))
				.thenReturn(Optional.of(authUser));

		when(passwordEncoder.matches(loginRequest.getPassword(), authUser.getPassword())).thenReturn(true);

		BaseException exception = assertThrows(BaseException.class, () -> authServiceImpl.login(loginRequest));
		assertThat(exception.getErrorMessage().getErrorType()).isEqualTo(ErrorType.ACCOUNT_NOT_VERIFIED);

		verify(authUserRepository).findByUsernameOrEmail(loginRequest.getEmailOrUsername());
		verify(passwordEncoder).matches(loginRequest.getPassword(), authUser.getPassword());
		verifyNoInteractions(roleRepository, refreshTokenService, jwtService, verificationTokenService, otpTokenService,
				authMapper);

	}

}
