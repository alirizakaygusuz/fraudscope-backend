package com.finscope.fraudscope.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.account.dto.DtoAccount;
import com.finscope.fraudscope.account.dto.DtoAccountIU;
import com.finscope.fraudscope.account.entity.Account;
import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;

@Mapper(config = CentralMapperConfig.class)
public interface AccountMapper extends BaseMapper<Account, DtoAccount, DtoAccountIU> {

	@Override
	DtoAccount toDto(Account entity);

	@Override
	Account toEntity(DtoAccountIU dtoIU);

	@Override
	void updateFromDtoIU(DtoAccountIU dtoIU, @MappingTarget Account entity);

	default Account map(Long id) {
		if (id == null)
			return null;

		return Account.builder().id(id).build();
	}
}
