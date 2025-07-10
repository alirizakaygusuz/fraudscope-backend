package com.finscope.fraudscope.authentication.refreshtoken.service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.refreshtoken.entity.RefreshToken;

public interface RefreshTokenService {

	RefreshToken build(AuthUser authUser, String ipAddress, String userAgent);
	
	RefreshToken save(RefreshToken refreshToken);
	
	RefreshToken createAndSave(AuthUser user, String ip, String agent);
}
