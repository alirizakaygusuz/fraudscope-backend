package com.finscope.fraudscope.authentication.helper;

import java.time.LocalDateTime;
import java.util.Set;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.authentication.verification.otp.dto.OtpTokenRequest;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;

public final class AuthTestDataFactory {

	private AuthTestDataFactory() {

	}

	public static RegisterRequest createValidRegisterRequest() {
		RegisterRequest request =new RegisterRequest();
		request.setUsername("ValidUsername");
		request.setEmail("valid@email.com");
		request.setPassword("validPassword123!");
		request.setIpAddress("127.0.0.1");
		request.setUserAgent("ValidUserAgent");

		return request;
	}
	
	public static RegisterResponse createValidRegisterResponse() {
		RegisterResponse response = new RegisterResponse();
		response.setEmail("valid@email.com");
		response.setUsername("ValidUsername");
		response.setMessage("Please verify your email!!!");
		
		return response;
	}
	
	public static Role createValidUserRole() {
		Role role = new Role();
		role.setName("USER");
		return role;
	}

	public static AuthUser createValidAuthUser() {
		AuthUser user=  AuthUser
				.builder()
				.username("ValidUsername")
				.email("valid@email.com")
				.password("encoded-password")
				.isEnabled(false)
				.twoFactorEnabled(true)
				.build();
		
		Role role = createValidUserRole();
		RoleUser roleUser = RoleUser.builder().role(role).authUser(user).build();
		user.setUserRoles(Set.of(roleUser));
		return user;

	}
	
	public static AuthUser createValidAuthUserWithEnabled() {
		AuthUser user = createValidAuthUser();
		user.setEnabled(true);
		return user;
	}
	
	public static AuthUser createValidAuthUserWithEnabledAndDisable2FA() {
		AuthUser user = createValidAuthUser();
		user.setEnabled(true);
		user.setTwoFactorEnabled(false);
		return user;
	}
	
	public static VerificationToken createValidVerificationToken() {
		return VerificationToken.builder()
				.verificationToken("mock-token")
				.authUser(createValidAuthUser())
				.tokenPurpose(TokenPurpose.ACCOUNT_VERIFICATION)
				.expiryDateTime(LocalDateTime.now().plusMinutes(240))
				.tokenStatus(TokenStatus.ACTIVE)
				.build();
	}
	
	public static VerificationToken createBlockedVerificationToken() {
		VerificationToken verificationToken = createValidVerificationToken();
		verificationToken.setTokenStatus(TokenStatus.BLOCKED);
		
		return verificationToken;
	}

	public static VerificationToken createExpiredVerificationToken() {
		VerificationToken verificationToken = createValidVerificationToken();
		verificationToken.setExpiryDateTime(LocalDateTime.now());
		
		return verificationToken;
	}
	
	public static LoginRequest createValidLoginRequest() {
		LoginRequest loginRequest = new LoginRequest();
		loginRequest.setEmailOrUsername("validEmailorUsername");
		loginRequest.setPassword("validPassword123!");
		loginRequest.setIpAddress("127.0.0.1");
		loginRequest.setUserAgent("ValidUserAgent");
		
		return loginRequest;
	}

	public static RefreshToken createValidRefreshToken() {
		AuthUser authUser = createValidAuthUserWithEnabled();
		
		return RefreshToken.builder()
				.token("Valid-Refresh-Token")
				.ipAddress("127.0.0.1")
				.userAgent("ValidUserAgent")
				.authUser(authUser)
				.build();
	}

	public static LoginResponse createValidLoginResponse() {
		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setAccessToken("Valid-Access-Token");
		loginResponse.setRefreshToken("Valid-Refresh-Token");
		loginResponse.setUsername("ValidUsername");
		loginResponse.setEmail("valid@email.com");
		
		
		return loginResponse;
	}
	
	
	public static OtpTokenRequest createOtpTokenRequest(String otpVerificationToken, String otpCode) {
		OtpTokenRequest request = new OtpTokenRequest();
		request.setOtpVerificationToken(otpVerificationToken);
		request.setOtpCode(otpCode);
		return request;
	}

	public static OtpToken createOtpToken(String otpVerificationToken, String otpCode, TokenStatus status, int attemptCount) {
		AuthUser authUser = createValidAuthUserWithEnabled();
		return OtpToken.builder()
				.otpVerificationToken(otpVerificationToken)
				.otpCode(otpCode)
				.tokenStatus(status)
				.attemptCount(attemptCount)
				.authUser(authUser)
				.expiryDateTime(LocalDateTime.now().plusMinutes(3))
				.ipAddress("127.0.0.1")
				.userAgent("JUnit")
				.build();
	}

	
}
