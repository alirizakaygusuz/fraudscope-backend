package com.finscope.fraudscope.authorization.permission.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.authorization.permission.dto.DtoPermission;
import com.finscope.fraudscope.authorization.permission.dto.DtoPermissionIU;
import com.finscope.fraudscope.authorization.permission.entity.Permission;
import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;

@Mapper(config = CentralMapperConfig.class)
public interface PermissionMapper extends BaseMapper<Permission, DtoPermission, DtoPermissionIU> {

	@Override
	DtoPermission toDto(Permission permission);

	@Override
	Permission toEntity(DtoPermissionIU dtoPermissionIU);

	@Override
	void updateFromDtoIU(DtoPermissionIU dtoPermissionIU, @MappingTarget Permission permission);

}
