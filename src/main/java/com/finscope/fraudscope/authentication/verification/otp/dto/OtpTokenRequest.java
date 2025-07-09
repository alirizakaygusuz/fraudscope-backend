package com.finscope.fraudscope.authentication.verification.otp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request body for OTP verification stepm in 2FA flow")
public class OtpTokenRequest {
   
	@Schema(description = "One-time password code sent to user", example = "726431")
	@NotBlank(message = "OTP code cannot be blank")
	private String otpCode;
	
    @Schema(description = "Token used to identify OTP session", example = "e45a14fc-1f36-4b6f-a9f1-26aa2a287f85")
	@NotBlank(message = "OTP verification token cannot be blank")
	private String otpVerificationToken;

}
