package com.finscope.fraudscope.user.service;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.user.enduser.entity.EndUser;

public interface UserService {
	
	public void throwIfAccountExists(Long  authUserId);
	
	public EndUser fetchEndUserOrThrow(Long authUserId);
	
	
	public AuthUser fetchAuthUserOrThrow(String username); 

}
