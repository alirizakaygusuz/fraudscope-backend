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
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "auth_users")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
@SQLDelete(sql = "UPDATE auth_users SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class AuthUser extends SoftDeletableAuditBase implements UserDetails {

	@Column(name = "username", unique = true, nullable = false)
	private String username;

	@Column(name = "email", unique = true, nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;
  
	@Builder.Default
	@Column(name = "is_enabled", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private boolean isEnabled = false;
	
    @Builder.Default
	@Column(name = "two_factor_enabled", nullable = false , columnDefinition ="BOOLEAN DEFAULT TRUE" )
	private boolean twoFactorEnabled = true;

	@OneToMany(mappedBy = "authUser", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	private Set<RoleUser> userRoles;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userRoles.stream().flatMap(roleUser -> roleUser.getRole().getRolePermissions().stream())
				.filter(RolePermission::isActive) // Active Permission Control
				.map(rolePermission -> new SimpleGrantedAuthority(rolePermission.getPermission().getName()))
				.collect(Collectors.toSet());
	}

	
	
	//Handle  later!!!!!!!!!!!!!!!!! 
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

}
