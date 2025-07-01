package com.finscope.fraudscope.common.enums;

public enum PredefinedRole implements BaseEnum  {
	USER("USER"),
	SUPER_ADMIN("SUPER_ADMIN");
	
	private String code;

	PredefinedRole(String code) {
        this.code = code;
    }
	
	@Override
	public String getCode() {
		return code;
	}
	
}
