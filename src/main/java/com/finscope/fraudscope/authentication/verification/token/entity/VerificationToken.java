package com.finscope.fraudscope.authentication.verification.token.entity;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authentication.verification.AbstractVerificationTokenBase;
import com.finscope.fraudscope.authentication.verification.MailTokenPayload;
import com.finscope.fraudscope.authentication.verification.enums.TokenStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "verification_tokens")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class VerificationToken extends AbstractVerificationTokenBase implements MailTokenPayload {

	@Column(name = "verification_token", nullable = false, unique = true)
	private String verificationToken;

	@OneToOne(targetEntity = AuthUser.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_user_id", nullable = false, unique = true)
	private AuthUser authUser;

	@Override
	public String getTokenValue() {
		return this.verificationToken;
	}

	@Override
	public String getRecipientEmail() {
		return this.getAuthUser().getEmail();
	}

	@Override
	public TokenStatus getTokenStatus() {
		return super.getTokenStatus();
	}

}
