package com.finscope.fraudscope.authentication.verification;

import java.time.LocalDateTime;

import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.common.audit.AuditBase;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@MappedSuperclass
@SuperBuilder
@NoArgsConstructor(force = true)
public class AbstractVerificationTokenBase extends AuditBase {

	@Enumerated(EnumType.STRING)
	private TokenPurpose tokenPurpose;

	@Enumerated(EnumType.STRING)
	private TokenStatus tokenStatus;

	@Column(name = "expiry_date_time", nullable = false)
	private LocalDateTime expiryDateTime;
	
	

}
