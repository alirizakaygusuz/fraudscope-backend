package com.finscope.fraudscope.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(description = "Response when OTP is required for 2FA verification")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OtpRequiredResponse {
	
	@Schema(description = "Token used for verifying OTP", example = "e45a14fc-1f36-4b6f-a9f1-26aa2a287f85")
	private String otpVerificationToken;

	@Schema(description = "Information message", example = "OTP has been sent to your email address.")
	private String message = "OTP has been sent to your email address.";

	@Schema(description = "OTP expiry time in seconds")
	private long expiresInSeconds;
}
