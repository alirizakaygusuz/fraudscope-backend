package com.finscope.fraudscope.transaction.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.finscope.fraudscope.common.config.CentralMapperConfig;
import com.finscope.fraudscope.common.mapper.BaseMapper;
import com.finscope.fraudscope.transaction.dto.DtoTransaction;
import com.finscope.fraudscope.transaction.dto.DtoTransactionIU;
import com.finscope.fraudscope.transaction.entity.Transaction;

@Mapper(config = CentralMapperConfig.class)
public interface TransactionMapper extends BaseMapper<Transaction, DtoTransaction, DtoTransactionIU> {

	@Override
	DtoTransaction toDto(Transaction transaction);

	@Override
	Transaction toEntity(DtoTransactionIU dtoTransactionIU);

	@Override
	void updateFromDtoIU(DtoTransactionIU dtoTransactionIU, @MappingTarget Transaction transaction);

}