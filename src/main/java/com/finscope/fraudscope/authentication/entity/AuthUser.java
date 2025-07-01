package com.finscope.fraudscope.authentication.entity;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.finscope.fraudscope.authorization.rolepermission.entity.RolePermission;
import com.finscope.fraudscope.authorization.roleuser.entity.RoleUser;
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
@Table(name = "auth_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SQLDelete(sql = "UPDATE auth_users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class AuthUser extends SoftDeletableAuditBase implements UserDetails {

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "is_enabled", nullable = false)
	private boolean isEnabled = false;

	@OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL)
	private Set<RoleUser> userRoles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userRoles.stream().flatMap(roleUser -> roleUser.getRole().getRolePermissions().stream())
				.filter(RolePermission::isActive) // Active Permission Control
				.map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().getName()))
				.collect(Collectors.toSet());
	}

}
