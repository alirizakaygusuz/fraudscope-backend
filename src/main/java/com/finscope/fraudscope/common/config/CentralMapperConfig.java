package com.finscope.fraudscope.common.config;

import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

@MapperConfig(
		componentModel = "spring" , 
	    unmappedTargetPolicy = ReportingPolicy.WARN
	    

)
public interface CentralMapperConfig {

}
