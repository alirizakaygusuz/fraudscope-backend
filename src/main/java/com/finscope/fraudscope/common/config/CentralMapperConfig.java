package com.finscope.fraudscope.common.config;

import org.mapstruct.Builder;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
		componentModel = "spring" , 
	    unmappedTargetPolicy = ReportingPolicy.WARN,
	    builder = @Builder(disableBuilder = true)

)
public interface CentralMapperConfig {

}
