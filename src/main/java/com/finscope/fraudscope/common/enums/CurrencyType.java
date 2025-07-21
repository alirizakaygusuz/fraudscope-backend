package com.finscope.fraudscope.common.enums;

public enum CurrencyType implements BaseEnum {

	TRY("currency.try"),
	USD("currency.usd"),
	EUR("currency.eur"),
	AUD("currency.aud"),
	NZD("currency.nzd");
	
	
	//i18n -> internationalization
	private final String i18n;
	
	private CurrencyType(String i18n) {
		this.i18n = i18n;
	}


	@Override
	public String getCode() {
		return i18n;
	}
	
	
	
	
}
