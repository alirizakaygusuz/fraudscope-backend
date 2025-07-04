package com.finscope.fraudscope.authentication.service.impl;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.LoginRequest;
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

	private final AuthMapper authMapper;

	@Value("${jwt.refresh.token.expiration-minutes}")
	private long refreshTokenExpiration;

	@Override
	public RegisterResponse register(RegisterRequest registerRequest) {

		throwIfUserExists(registerRequest);

		AuthUser authUser = buildAuthUserWithDefaultRole(registerRequest);
		AuthUser savedUser = authUserRepository.save(authUser);

		// Create Verification Token
		VerificationToken verificationToken = verificationTokenService.createVerificationToken(savedUser,
				TokenPurpose.ACCOUNT_VERIFICATION);

		// Send verification email
		verificationTokenService.sendVerificationEmail(verificationToken);

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

		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setToken(UUID.randomUUID().toString());
		refreshToken.setAuthUser(savedAuthUser);
		refreshToken.setExpiryDate(LocalDateTime.now().plusMinutes(refreshTokenExpiration));
		refreshToken.setIpAddress(ipAddress);
		refreshToken.setUserAgent(userAgent);

		refreshTokenRepository.save(refreshToken);

		return refreshToken;
	}

	private AuthUser buildAuthUserWithDefaultRole(RegisterRequest registerRequest) {

		AuthUser authUser = new AuthUser();
		authUser.setUsername(registerRequest.getUsername());
		authUser.setEmail(registerRequest.getEmail());
		authUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

		// ENUM base Role Asssign
		Role userRole = roleRepository.findByName(PredefinedRole.USER.name())
				.orElseThrow(() -> new BaseException(new ErrorMessage(ErrorType.ROLE_NOT_FOUND)));

		RoleUser roleUser = new RoleUser();
		roleUser.setRole(userRole);
		roleUser.setAuthUser(authUser);

		authUser.setUserRoles(Set.of(roleUser));

		return authUser;
	}

	@Override
	public LoginResponse login(LoginRequest loginRequest) {

		AuthUser authUser = validateLogin(loginRequest.getEmailOrUsername(), loginRequest.getPassword());

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
