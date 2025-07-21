package com.finscope.fraudscope.authorization.role.enums;

import com.finscope.fraudscope.common.enums.BaseEnum;

public enum PredefinedRole implements BaseEnum {
	USER, 
	SUPER_ADMIN;

	@Override
	public String getCode() {
		return name();
	}

}
