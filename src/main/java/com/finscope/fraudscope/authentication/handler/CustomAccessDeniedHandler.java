package com.finscope.fraudscope.authentication.handler;

import java.io.IOException;
import java.util.Date;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
	                   AccessDeniedException accessDeniedException) throws IOException, ServletException {

	    response.setContentType("application/json");
	    response.setStatus(HttpServletResponse.SC_FORBIDDEN);

	    String message = "error_type.access_denied";

	    ErrorContext<String> errorContext = new ErrorContext<>(
	        request.getRequestURI(),
	        new Date(),
	        request.getServerName(),
	        message
	    );

	    ApiError<String> apiError = new ApiError<>(HttpServletResponse.SC_FORBIDDEN, errorContext);

	    response.getWriter().write(objectMapper.writeValueAsString(apiError));
	}

}
