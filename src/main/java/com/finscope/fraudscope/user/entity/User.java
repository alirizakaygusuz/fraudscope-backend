package com.finscope.fraudscope.user.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class User extends SoftDeletableAuditBase{
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "auth_user_id", nullable = false,unique = true )
	private AuthUser authUser;
	
	@Column(name = "phone_number" , length = 20)
	private String phoneNumber;
	
	@Column(nullable = false, name = "name")
	private String name;
	
	@Column(nullable = false,name = "surname")
	private String surname;
}
