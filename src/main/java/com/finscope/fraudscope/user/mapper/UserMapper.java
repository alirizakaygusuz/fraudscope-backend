package com.finscope.fraudscope.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;
import com.finscope.fraudscope.user.dto.DtoUser;
import com.finscope.fraudscope.user.dto.DtoUserIU;
import com.finscope.fraudscope.user.entity.User;

@Mapper(config = CentralMapperConfig.class)
public interface UserMapper extends BaseMapper<User, DtoUser, DtoUserIU> {

	@Override
	DtoUser toDto(User user);

	@Override
	User toEntity(DtoUserIU dtoUserIU);

	@Override
	void updateFromDtoIU(DtoUserIU dtoUserIU, @MappingTarget User user);

}
