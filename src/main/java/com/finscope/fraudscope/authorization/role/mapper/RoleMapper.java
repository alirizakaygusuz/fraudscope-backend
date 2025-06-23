package com.finscope.fraudscope.authorization.role.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.authorization.role.dto.DtoRole;
import com.finscope.fraudscope.authorization.role.dto.DtoRoleIU;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;

@Mapper(config = CentralMapperConfig.class)
public interface RoleMapper extends BaseMapper<Role, DtoRole, DtoRoleIU> {
	@Override
	DtoRole toDto(Role role);

	@Override
	Role toEntity(DtoRoleIU dtoRoleIU);

	@Override
	void updateFromDtoIU(DtoRoleIU dtoIU, @MappingTarget Role role);
}
