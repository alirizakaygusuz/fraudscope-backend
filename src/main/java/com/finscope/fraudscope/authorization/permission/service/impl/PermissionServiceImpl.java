package com.finscope.fraudscope.authorization.permission.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.finscope.fraudscope.authorization.permission.dto.DtoPermission;
import com.finscope.fraudscope.authorization.permission.dto.DtoPermissionIU;
import com.finscope.fraudscope.authorization.permission.entity.Permission;
import com.finscope.fraudscope.authorization.permission.mapper.PermissionMapper;
import com.finscope.fraudscope.authorization.permission.repository.PermissionRepository;
import com.finscope.fraudscope.authorization.permission.service.PermissionService;
import com.finscope.fraudscope.common.exception.BaseException;
import com.finscope.fraudscope.common.exception.ErrorMessage;
import com.finscope.fraudscope.common.exception.enums.ErrorType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

	private final PermissionRepository permissionRepository;
	private final PermissionMapper permissionMapper;

	@Override
	public DtoPermission getPermission(String name) {
		Permission permission = fetchPermissionOrThrow(name);
		
		return permissionMapper.toDto(permission);
	}

	@Override
	public List<DtoPermission> getAllPermissions() {
		List<Permission> permissions = permissionRepository.findAll();
		
		List<DtoPermission> dtoPermissions = new ArrayList<>();
		
		
		for(Permission permission: permissions) {
			dtoPermissions.add(permissionMapper.toDto(permission));
		}
		
		return dtoPermissions;
	}

	@Override
	public DtoPermission updatePermissionDescription(DtoPermissionIU dtoPermissionIU) {
		Permission permission = fetchPermissionOrThrow(dtoPermissionIU.getName());
		permissionMapper.updateFromDtoIU(dtoPermissionIU, permission);
		
		Permission updatedPermission = permissionRepository.save(permission);
		
		return permissionMapper.toDto(updatedPermission);
	}
	
	private Permission fetchPermissionOrThrow(String name) {
		return permissionRepository.findByName(name)
			     .orElseThrow(() -> new BaseException( new ErrorMessage(ErrorType.PERMISSION_NOT_FOUND)));
	
	}

}
