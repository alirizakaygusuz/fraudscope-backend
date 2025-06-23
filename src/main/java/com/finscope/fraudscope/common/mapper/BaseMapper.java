package com.finscope.fraudscope.common.mapper;

import org.mapstruct.MappingTarget;

public interface BaseMapper<Entity, Dto, DtoIU> {

	Dto toDto(Entity entity);

	Entity toEntity(DtoIU dtoIU);

	void updateFromDtoIU(DtoIU dtoIU, @MappingTarget Entity entity);

}
