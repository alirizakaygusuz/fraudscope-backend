package com.finscope.fraudscope.authorization.permission.service;

import java.util.List;

import com.finscope.fraudscope.authorization.permission.dto.DtoPermission;
import com.finscope.fraudscope.authorization.permission.dto.DtoPermissionIU;

public interface PermissionService {

	
	public DtoPermission getPermission(String name);
	
	public List<DtoPermission> getAllPermissions();
	
	public DtoPermission updatePermissionDescription(DtoPermissionIU dtoPermissionIU);
	
	
	
}
