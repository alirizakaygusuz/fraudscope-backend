package com.finscope.fraudscope.authentication.service;

import com.finscope.fraudscope.authentication.dto.LoginRequest;
import com.finscope.fraudscope.authentication.dto.RegisterRequest;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;

public interface AuthService {

	RegisterResponse register(RegisterRequest registerRequest);

	Object login(LoginRequest loginRequest);


}
