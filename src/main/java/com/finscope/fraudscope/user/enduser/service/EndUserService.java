package com.finscope.fraudscope.user.enduser.service;

import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUserIU;

public interface EndUserService {

	public DtoEndUser completeEndUserProfile(DtoEndUserIU dtoUserIU,String currentUsername);
	
	public DtoEndUser getEndUserProfileDetails(String currentUsername);
	
	public void deleteEndUser(String currentUsername);
	
	public DtoEndUser updateEndUserProfile(DtoEndUserIU dtoUserIU,String currentUsername);
	
}
