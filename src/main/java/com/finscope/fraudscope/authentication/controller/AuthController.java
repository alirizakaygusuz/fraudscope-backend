package com.finscope.fraudscope.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.service.AuthService;
import com.finscope.fraudscope.common.controller.BaseResponseController;
import com.finscope.fraudscope.common.response.StandartResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Tag(name = "Authentication" ,description =  "Endpoints for user registration and login.")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseResponseController {

	private final AuthService authUserService;

	@Operation(
			summary = "Create a new user account", 
			description = "Creates a new user account with email verification pending.The user account will be disabled until the user verifies the email"
			)
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "201", description = "User registered successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid username or email"),
			@ApiResponse(responseCode = "409", description = "Username or email already exists"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/register")
	public ResponseEntity<StandartResponse<RegisterResponse>> register(@Valid @RequestBody RegisterRequest request,
			HttpServletRequest httpServletRequest) {

		request.injectClientMetaData(httpServletRequest);

		return created(authUserService.register(request));
	}

	
	@Operation(
			summary = "Log in to the system", 
			description = "Authenticates a user with either email or username and a passowrd"
			)
	@ApiResponses(value = { 
			@ApiResponse(responseCode = "200", description = "User logged in successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid  email/username or password"),
			@ApiResponse(responseCode = "403", description = "Account not verified"),
			@ApiResponse(responseCode = "500", description = "Internal server error") })
	@PostMapping("/login")
	public ResponseEntity<StandartResponse<Object>> login(@Valid @RequestBody LoginRequest request,
			HttpServletRequest httpServletRequest) {

		request.injectClientMetaData(httpServletRequest);

		return ok(authUserService.login(request));
	}

}