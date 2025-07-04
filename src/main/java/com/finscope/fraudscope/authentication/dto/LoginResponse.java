package com.finscope.fraudscope.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {

	private String accessToken;
	
	private String refreshToken;
	
	private String username;
	
	private String email;
}
