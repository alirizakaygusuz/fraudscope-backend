package com.finscope.fraudscope.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.service.AuthService;
import com.finscope.fraudscope.common.controller.BaseResponseController;
import com.finscope.fraudscope.common.response.ApiResponse;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController extends BaseResponseController {

	private final AuthService authUserService;

	@PostMapping("/register")
	public ResponseEntity<ApiResponse<RegisterResponse>> register(@RequestBody RegisterRequest request,
			HttpServletRequest httpServletRequest) {

		request.injectClientMetaData(httpServletRequest);

		return created(authUserService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody LoginRequest request,
			HttpServletRequest httpServletRequest) {

		request.injectClientMetaData(httpServletRequest);

		return ok(authUserService.login(request));
	}

}