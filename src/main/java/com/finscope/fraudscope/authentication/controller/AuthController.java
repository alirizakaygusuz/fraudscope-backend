package com.finscope.fraudscope.authentication.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.dto.AuthReponse;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.service.AuthService;
import com.finscope.fraudscope.common.util.IpUtils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authUserService;

	@PostMapping("/register")
	public ResponseEntity<AuthReponse> register(@RequestBody RegisterRequest request,
			HttpServletRequest httpServletRequest) {

		request.setIpAddress(IpUtils.getClientIp(httpServletRequest));
		request.setUserAgent(IpUtils.getUserAgent(httpServletRequest));

		return ResponseEntity.ok(authUserService.register(request));
	}

}