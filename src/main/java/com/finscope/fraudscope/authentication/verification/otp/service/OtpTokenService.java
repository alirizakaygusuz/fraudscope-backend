package com.finscope.fraudscope.authentication.verification.otp.service;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.otp.entity.OtpToken;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;

public interface OtpTokenService {
	OtpToken createOtpToken(AuthUser authUser,LoginRequest loginRequest,TokenPurpose tokenPurpose);

}
