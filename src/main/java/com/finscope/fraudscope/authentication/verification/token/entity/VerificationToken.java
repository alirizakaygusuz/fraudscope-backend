package com.finscope.fraudscope.authentication.verification.token.entity;

import java.time.LocalDateTime;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.verification.enums.TokenPurpose;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;
import com.finscope.fraudscope.common.audit.AuditBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VerificationToken extends AuditBase {

	@Column(name = "verification_token", nullable = false, unique = true)
	private String verificationToken;

	@Enumerated(EnumType.STRING)
	private TokenPurpose tokenPurpose;

	@OneToOne(targetEntity = AuthUser.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_user_id", nullable = false)
	private AuthUser authUser;

	@Enumerated(EnumType.STRING)
	private TokenStatus tokenStatus;

	@Column(name = "expiry_date_time", nullable = false)
	private LocalDateTime expiryDateTime;

}
