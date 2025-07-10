package com.finscope.fraudscope.authentication.verification.otp.service;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.otp.dto.OtpTokenRequest;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;

public interface OtpTokenService {
	OtpToken createOtpToken(AuthUser authUser,LoginRequest loginRequest,TokenPurpose tokenPurpose);

	LoginResponse completeLoginWithOtp(OtpTokenRequest request);
	
}
