package com.finscope.fraudscope.user.entity;

import java.util.List;
import java.util.Set;

import com.finscope.fraudscope.account.entity.Account;
import com.finscope.fraudscope.auth.entity.RoleUser;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
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
	
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
	private List<Account> accounts;
	
	@OneToMany(mappedBy = "user" ,cascade = CascadeType.ALL)
	private Set<RoleUser> userRoles;
}
