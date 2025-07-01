package com.finscope.fraudscope.authentication.handler;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
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
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
	
	private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        ErrorDetails errorDetails = new ErrorDetails("Forbidden access", null, "AUTH_403");

        ApiResponse<ErrorDetails> apiResponse = ApiResponse.forbidden(errorDetails);

        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
       
    }
}
