package com.finscope.fraudscope.authentication.mapper;

import org.mapstruct.Mapper;

import com.finscope.fraudscope.authentication.dto.LoginResponse;
import com.finscope.fraudscope.authentication.dto.RegisterResponse;
import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.common.config.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface AuthMapper {
	
	LoginResponse toLoginResponse(AuthUser authUser , String accessToken, String refreshToken);

	RegisterResponse toRegisterResponse(AuthUser authUser);
}
