package com.finscope.fraudscope.user.enduser.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUser;
import com.finscope.fraudscope.user.enduser.dto.DtoEndUserIU;
import com.finscope.fraudscope.user.enduser.entity.EndUser;

@Mapper(config = CentralMapperConfig.class)
public interface EndUserMapper extends BaseMapper<EndUser, DtoEndUser, DtoEndUserIU> {

	@Override
	@Mapping(target = "authUser", ignore = true)
	DtoEndUser toDto(EndUser user);

	@Override
	@Mapping(target = "authUser", ignore = true)
	EndUser toEntity(DtoEndUserIU dtoUserIU);

	@Override
	@Mapping(target = "authUser", ignore = true)
	void updateFromDtoIU(DtoEndUserIU dtoUserIU, @MappingTarget EndUser user);

}
