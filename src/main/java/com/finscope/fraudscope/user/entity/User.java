package com.finscope.fraudscope.user.entity;

import java.util.List;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.account.entity.Account;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
	    name = "users",
	    indexes = {
	        @Index(name = "idx_users_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class User extends SoftDeletableAuditBase {

	@Column(unique = true)
	private String username;

	@Column(unique = true)
	private String email;

	private String password;

	private boolean isEnabled = false;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Account> accounts;

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<RoleUser> userRoles;
}
