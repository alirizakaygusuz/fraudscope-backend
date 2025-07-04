package com.finscope.fraudscope.authentication.handler;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finscope.fraudscope.common.response.ApiError;
import com.finscope.fraudscope.common.response.ErrorContext;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

		String message = "error_type.unauthorized_access";

		ErrorContext<String> errorContext = new ErrorContext<>(request.getRequestURI(), new Date(),
				request.getServerName(), message);

		ApiError<String> apiError = new ApiError<>(HttpServletResponse.SC_UNAUTHORIZED, errorContext);

		response.getWriter().write(objectMapper.writeValueAsString(apiError));

	}

}
