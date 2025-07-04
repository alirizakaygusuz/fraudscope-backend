package com.finscope.fraudscope.authentication.service;

import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;

public interface AuthService {

	RegisterResponse register(RegisterRequest registerRequest);
	
	
	LoginResponse login(LoginRequest loginRequest);
	
	
	
	
	
	
}
