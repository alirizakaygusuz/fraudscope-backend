package com.finscope.fraudscope.authentication.refreshtoken.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.finscope.fraudscope.authentication.refreshtoken.dto.RefreshTokenRequest;
import com.finscope.fraudscope.authentication.refreshtoken.dto.RefreshTokenResponse;
import com.finscope.fraudscope.authentication.refreshtoken.service.RefreshTokenService;
import com.finscope.fraudscope.common.controller.BaseResponseController;
import com.finscope.fraudscope.common.response.StandartResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/api/auth")
@RestController
@AllArgsConstructor
public class RefreshTokenContoller extends BaseResponseController {

	private final RefreshTokenService refreshTokenService;

	@PostMapping("/refresh-token/rotate")
	public ResponseEntity<StandartResponse<RefreshTokenResponse>> rotateToken(
			@Valid @RequestBody RefreshTokenRequest request,HttpServletRequest httpServletRequest) {
		request.injectClientMetaData(httpServletRequest);

		return ok(refreshTokenService.rotateToken(request));

	}
	
	@PostMapping("/logout")
	public ResponseEntity<StandartResponse<String>> logout(@Valid @RequestBody RefreshTokenRequest request,HttpServletRequest httpServletRequest) {
		request.injectClientMetaData(httpServletRequest);

		return ok(refreshTokenService.logout(request));

	}
}
