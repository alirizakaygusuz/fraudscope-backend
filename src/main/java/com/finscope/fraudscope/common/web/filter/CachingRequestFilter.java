package com.finscope.fraudscope.common.web.filter;

import java.io.IOException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.finscope.fraudscope.common.web.wrapper.MultiReadHttpServletRequest;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class CachingRequestFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String path = request.getRequestURI();

		if (shouldCache(path)) {
			log.info("[CachingRequestFilter] -> Incoming request to {}", request.getRequestURI());

			// Previously got " No content to map due to end-of-input" with
			// ContentCachingRequestWrapper class when reading request body
			// Fixed with MultiReadHttpServletRequest to allow multiple body reads!!
			MultiReadHttpServletRequest wrappedRequest = new MultiReadHttpServletRequest(request);

			request.setAttribute("cachedAuthRequest", wrappedRequest);

			filterChain.doFilter(wrappedRequest, response);

		} else {
			filterChain.doFilter(request, response);
		}

	}

	private boolean shouldCache(String path) {
		return path.endsWith("/login") || path.endsWith("/verify-otp") || path.endsWith("/register");
	}

}
