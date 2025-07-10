package com.finscope.fraudscope.authentication.refreshtoken.service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.dto.RefreshTokenRequest;
import com.finscope.fraudscope.authentication.refreshtoken.dto.RefreshTokenResponse;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;

public interface RefreshTokenService {
	
	RefreshToken createAndSave(AuthUser user, String ip, String agent);
	
	RefreshTokenResponse rotateToken(RefreshTokenRequest request);
	
	String logout(RefreshTokenRequest request);
}
