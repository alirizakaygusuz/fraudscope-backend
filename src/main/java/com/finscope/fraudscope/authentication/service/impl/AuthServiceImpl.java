package com.finscope.fraudscope.authentication.service.impl;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.OtpRequiredResponse;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.jwt.JWTService;
import com.finscope.fraudscope.authentication.mapper.AuthMapper;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.refreshtoken.repository.RefreshTokenRepository;
import com.finscope.fraudscope.authentication.repository.AuthUserRepository;
import com.finscope.fraudscope.authentication.service.AuthService;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.otp.service.OtpTokenService;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.authentication.verification.token.service.VerificationTokenService;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.role.repository.RoleRepository;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;
import com.finscope.fraudscope.common.enums.PredefinedRole;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final BCryptPasswordEncoder passwordEncoder;

	private final AuthUserRepository authUserRepository;

	private final RoleRepository roleRepository;

	private final RefreshTokenRepository refreshTokenRepository;

	private final JWTService jwtService;

	private final VerificationTokenService verificationTokenService;

	private final OtpTokenService otpTokenService;

	private final AuthMapper authMapper;

	@Value("${jwt.refresh.token.expiration-minutes}")
	private long refreshTokenExpiration;

	@Value("${otp.expiration-seconds}")
	private long otpExpirationSeconds;

	@Override
	@Transactional(rollbackFor = BaseException.class)
	public RegisterResponse register(RegisterRequest registerRequest) {

		throwIfUserExists(registerRequest);

		AuthUser authUser = buildAuthUserWithDefaultRole(registerRequest);
		AuthUser savedUser = authUserRepository.save(authUser);

		//Create-Send email
		verificationTokenService.createVerificationToken(savedUser, TokenPurpose.ACCOUNT_VERIFICATION);

		RegisterResponse registerResponse = authMapper.toRegisterResponse(savedUser);
		registerResponse.setMessage("Please verify your email!!!");

		return registerResponse;
	}

	private void throwIfUserExists(RegisterRequest registerRequest) {
		if (authUserRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
			throw new BaseException(new ErrorMessage(ErrorType.USERNAME_ALREADY_EXISTS, registerRequest.getUsername()));

		}
		if (authUserRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
			throw new BaseException(new ErrorMessage(ErrorType.EMAIL_ALREADY_EXISTS, registerRequest.getEmail()));

		}
	}

	private RefreshToken createAndSaveRefreshToken(AuthUser savedAuthUser, String ipAddress, String userAgent) {
		return refreshTokenRepository.save(RefreshToken.builder().token(UUID.randomUUID().toString())
				.authUser(savedAuthUser).expiryDate(LocalDateTime.now().plusMinutes(refreshTokenExpiration))
				.ipAddress(ipAddress).userAgent(userAgent).build()

		);

	}

	private AuthUser buildAuthUserWithDefaultRole(RegisterRequest registerRequest) {
		AuthUser authUser = AuthUser.builder().username(registerRequest.getUsername()).email(registerRequest.getEmail())
				.password(passwordEncoder.encode(registerRequest.getPassword())).isEnabled(false).twoFactorEnabled(true)
				.build();

		// ENUM base Role Asssign
		Role userRole = roleRepository.findByName(PredefinedRole.USER.name())
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.ROLE_NOT_FOUND)));

		RoleUser roleUser = RoleUser.builder().role(userRole).authUser(authUser).build();

		authUser.setUserRoles(new HashSet<>(Set.of(roleUser)));

		return authUser;
	}

	@Override
	@Transactional(rollbackFor = BaseException.class)
	public Object login(LoginRequest loginRequest) {

		AuthUser authUser = validateLogin(loginRequest.getEmailOrUsername(), loginRequest.getPassword());

		if (authUser.isTwoFactorEnabled()) {
			OtpToken otpToken = otpTokenService.createOtpToken(authUser, loginRequest, TokenPurpose.TWO_FACTOR_AUTH);

			OtpRequiredResponse otpRequiredResponse = new OtpRequiredResponse();

			otpRequiredResponse.setOtpVerificationToken(otpToken.getOtpVerificationToken());
			otpRequiredResponse.setExpiresInSeconds(otpExpirationSeconds);
			otpRequiredResponse.setMessage("OTP CODE has been sent to your email.");

			return otpRequiredResponse;
		}

		RefreshToken refreshToken = createAndSaveRefreshToken(authUser, loginRequest.getIpAddress(),
				loginRequest.getUserAgent());

		String accessToken = jwtService.generateToken(authUser);

		return authMapper.toLoginResponse(authUser, refreshToken.getToken(), accessToken);

	}

	private AuthUser validateLogin(String usernameOrEmail, String password) {
		AuthUser authUser = authUserRepository.findByUsernameOrEmail(usernameOrEmail).orElseThrow(
				() -> new BaseException(new ErrorMessage(ErrorType.USERNAME_OR_EMAIL_NOT_FOUND, usernameOrEmail)));

		if (!passwordEncoder.matches(password, authUser.getPassword())) {
			throw new BaseException(new ErrorMessage(ErrorType.INVALID_PASSWORD, password));

		}

		if (!authUser.isEnabled()) {
			throw new BaseException(new ErrorMessage(ErrorType.ACCOUNT_NOT_VERIFIED));
		}

		return authUser;

	}

}
