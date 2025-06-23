package com.finscope.fraudscope.authorization.roleuser.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.authorization.roleuser.dto.DtoRoleUser;
import com.finscope.fraudscope.authorization.roleuser.dto.DtoRoleUserIU;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;
import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;

@Mapper(config = CentralMapperConfig.class)
public interface RoleUserMapper extends BaseMapper<RoleUser, DtoRoleUser, DtoRoleUserIU> {

	@Override
	DtoRoleUser toDto(RoleUser roleUser);

	@Override
	RoleUser toEntity(DtoRoleUserIU dtoRoleUserIU);

	@Override
	void updateFromDtoIU(DtoRoleUserIU dtoRoleUserIU, @MappingTarget RoleUser roleUser);

}
