package com.finscope.fraudscope.authentication.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finscope.fraudscope.common.response.ApiResponse;
import com.finscope.fraudscope.common.response.ErrorDetails;

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
        
        ErrorDetails errorDetails = new ErrorDetails("Unauthorized access", null, "AUTH_401");

        ApiResponse<ErrorDetails> apiResponse = ApiResponse.unauthorized(errorDetails);
        
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
        
		
	}

}
