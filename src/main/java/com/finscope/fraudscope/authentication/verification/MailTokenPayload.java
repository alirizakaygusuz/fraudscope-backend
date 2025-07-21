package com.finscope.fraudscope.authentication.verification;

import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;

public interface MailTokenPayload {

	String getTokenValue();
	
	String getRecipientEmail();
	
	TokenPurpose getTokenPurpose();
}
