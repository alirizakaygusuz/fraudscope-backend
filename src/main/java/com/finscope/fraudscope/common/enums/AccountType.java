package com.finscope.fraudscope.common.enums;

public enum AccountType implements BaseEnum{

	CURRENT("account.current"),
	SAVINGS("account.savings"),
	FOREIGN("account.foreign"),
	INVESTMENT("account.investment"),
	LOAN("account.loan");
	
	
	//i18n -> internationalization
	private final String i18n;
	
	private AccountType(String code) {
		this.i18n = code;
	}


	@Override
	public String getCode() {
		return i18n;
	}
}
