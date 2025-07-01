package com.finscope.fraudscope.authentication.mapper;

import org.mapstruct.Mapper;

import com.finscope.fraudscope.authentication.dto.AuthReponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.common.config.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface AuthMapper {
	AuthReponse toResponse(AuthUser authUser , String accessToken, String refreshToken);
}
