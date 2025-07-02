package com.finscope.fraudscope.authentication.verification.token.service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.token.entity.VerificationToken;

public interface VerificationTokenService {
	VerificationToken createVerificationToken(AuthUser authUser, TokenPurpose tokenPurpose);
	
	void sendVerificationEmail(VerificationToken verificationToken);

	void verifyAccount(String token);

}
