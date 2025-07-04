package com.finscope.fraudscope.authentication.dto;

import com.finscope.fraudscope.common.util.IpUtils;

import jakarta.servlet.http.HttpServletRequest;

public interface ClientMetadataAware {
	void setIpAddress(String ip);
	void setUserAgent(String userAgent);
	
	default void injectClientMetaData(HttpServletRequest request) {
		this.setIpAddress(IpUtils.getClientIp(request));
		this.setUserAgent(IpUtils.getUserAgent(request));
	}
}
