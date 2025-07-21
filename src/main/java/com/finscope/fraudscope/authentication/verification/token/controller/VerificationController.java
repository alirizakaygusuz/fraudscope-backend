package com.finscope.fraudscope.authentication.verification.token.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.verification.token.service.VerificationTokenService;
import com.finscope.fraudscope.common.response.StandartResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Token Verification", description = "Endpoints for verifying email confirmation tokens during registration.")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class VerificationController {

	private final VerificationTokenService verificationTokenService;

	
	@Operation(
		    summary = "Verify registration token",
		    description = "Activates the user account by verifying the token sent to the registered email address."
		)

	@ApiResponses(value = {
		    @ApiResponse(responseCode = "200", description = "Token verified successfully, user account activated"),
		    @ApiResponse(responseCode = "400", description = "Invalid or expired verification token"),
		    @ApiResponse(responseCode = "404", description = "Verification token not found"),
		    @ApiResponse(responseCode = "500", description = "Internal server error")
		})

	@GetMapping("/verify")
	public ResponseEntity<StandartResponse<?>> verifyAccount(@RequestParam String token) {

		verificationTokenService.verifyAccount(token);
		return ResponseEntity.ok(StandartResponse.ok("Your account has been successfully activated!"));

	}

}
