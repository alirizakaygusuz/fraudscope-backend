package com.finscope.fraudscope.authentication.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.finscope.fraudscope.authentication.jwt.JWTAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationProvider authenticationProvider;

	private final JWTAuthenticationFilter jwtAuthenticationFilter;

	private final AuthenticationEntryPoint authenticationEntryPoint;
	
	private final AccessDeniedHandler accessDeniedHandler;

	public static final String REGISTER = "/api/auth/register";

	public static final String LOGIN = "/api/auth/login";

	public static final String ACCOUNT_VERIFY = "/api/auth/verify";
	
	public static final String SWAGGER_UI="/swagger-ui/**";
	public static final String SWAGGER_API_DOCS = "/v3/api-docs/**";
	
	
	@Bean
	 SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
			.csrf(csrf -> csrf.disable())
			.securityContext(securityContext -> securityContext.requireExplicitSave(false))
			.authorizeHttpRequests(authorize -> authorize
				.requestMatchers(REGISTER, LOGIN,ACCOUNT_VERIFY,SWAGGER_UI,SWAGGER_API_DOCS).permitAll()
				.anyRequest().authenticated()
			)
			.exceptionHandling(handling -> handling
				.authenticationEntryPoint(authenticationEntryPoint)
				.accessDeniedHandler(accessDeniedHandler)
			)
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authenticationProvider(authenticationProvider)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.build();
	}

}
