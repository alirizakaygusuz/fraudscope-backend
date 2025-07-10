package com.finscope.fraudscope.authentication.refreshtoken.dto;

import com.finscope.fraudscope.authentication.dto.ClientMetadataAware;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequest implements ClientMetadataAware {

	private String refreshToken;
	
	private String ipAddress;
	
	private String userAgent;
}
