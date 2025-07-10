package com.finscope.fraudscope.authentication.verification.otp.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.verification.otp.dto.OtpTokenRequest;
import com.finscope.fraudscope.authentication.verification.otp.service.OtpTokenService;
import com.finscope.fraudscope.common.response.StandartResponse;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class OtpTokenController {

	private final OtpTokenService otpTokenService;
	
	@PostMapping("login/verify-otp")
	public StandartResponse<?> completeLoginWithOtp(@Valid @RequestBody OtpTokenRequest request) {
		return StandartResponse.ok(otpTokenService.completeLoginWithOtp(request));
	}
}
