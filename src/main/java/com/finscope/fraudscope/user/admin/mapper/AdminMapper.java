package com.finscope.fraudscope.user.admin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;
import com.finscope.fraudscope.user.admin.dto.DtoAdmin;
import com.finscope.fraudscope.user.admin.dto.DtoAdminIU;
import com.finscope.fraudscope.user.admin.entity.Admin;

@Mapper(config = CentralMapperConfig.class)

public interface AdminMapper extends BaseMapper<Admin, DtoAdmin, DtoAdminIU>  {

	
	@Override
	@Mapping(target = "authUser", ignore = true)
	DtoAdmin toDto(Admin admin);

	@Override
	@Mapping(target = "authUser", ignore = true)
	Admin toEntity(DtoAdminIU dtoAdminIU);

	@Override
	@Mapping(target = "authUser", ignore = true)
	void updateFromDtoIU(DtoAdminIU dtoAdminIU, @MappingTarget Admin admin);

}
