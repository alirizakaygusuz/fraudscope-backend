package com.finscope.fraudscope.user.admin.service;

import java.util.List;

import com.finscope.fraudscope.user.admin.dto.DtoAdmin;
import com.finscope.fraudscope.user.admin.dto.DtoAdminIU;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;

public interface AdminService {
	
	public DtoAdmin completeAdminProfile(DtoAdminIU dtoAdminIU,String currentUsername);

	public DtoAdmin getAdminProfileDetails(String currentUsername);
	
	public DtoAdmin updateAdminProfile(DtoAdminIU dtoAdminIU,String currentUsername);


	public List<DtoEndUser> getAllUserProfilesDetails();
	
	public DtoEndUser getSelectedUserProfilesDetails(String usernameOrEmail);

	
	public void deleteSelectedEndUser(String usernameOrEmail, String authorizedUsername);

}
