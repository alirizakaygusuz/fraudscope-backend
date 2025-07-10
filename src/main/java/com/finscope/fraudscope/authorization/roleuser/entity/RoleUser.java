package com.finscope.fraudscope.authorization.roleuser.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
	    name = "role_users",
	    uniqueConstraints = { @UniqueConstraint(columnNames = { "role_id", "user_id" }) },
	    indexes = {
	        @Index(name = "idx_role_users_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE role_users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class RoleUser extends SoftDeletableAuditBase {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id")
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "auth_user_id" , nullable = false)
	private AuthUser authUser;
}
