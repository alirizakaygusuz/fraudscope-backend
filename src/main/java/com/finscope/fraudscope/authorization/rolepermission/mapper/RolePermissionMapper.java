package com.finscope.fraudscope.authorization.rolepermission.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.authorization.permission.dto.DtoPermissionIU;
import com.finscope.fraudscope.authorization.rolepermission.dto.DtoRolePermission;
import com.finscope.fraudscope.authorization.rolepermission.entity.RolePermission;
import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;

@Mapper(config = CentralMapperConfig.class)
public interface RolePermissionMapper extends BaseMapper<RolePermission, DtoRolePermission, DtoPermissionIU> {

	@Override
	DtoRolePermission toDto(RolePermission rolePermission);

	@Override
	RolePermission toEntity(DtoPermissionIU dtoPermissionIU);

	@Override
	void updateFromDtoIU(DtoPermissionIU dtoPermissionIU, @MappingTarget RolePermission rolePermission);

}
