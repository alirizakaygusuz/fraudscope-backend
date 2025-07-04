package com.finscope.fraudscope.authentication.verification.token.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.verification.token.service.VerificationTokenService;
import com.finscope.fraudscope.common.response.ApiResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class VerificationController {

	private final VerificationTokenService verificationTokenService;

	@GetMapping("/verify")
	public ResponseEntity<ApiResponse<?>> verifyAccount(@RequestParam String token) {

		verificationTokenService.verifyAccount(token);
		return ResponseEntity.ok(ApiResponse.ok("Your account has been successfully activated!"));

	}

}
