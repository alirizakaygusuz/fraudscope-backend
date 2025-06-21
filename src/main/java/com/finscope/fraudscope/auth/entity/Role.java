package com.finscope.fraudscope.auth.entity;

import java.util.Objects;
import java.util.Set;

import com.finscope.fraudscope.common.audit.AuditBase;

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
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends AuditBase {

	@Column(unique = true , nullable = false , length = 64)
	private String name;
	
	
	@OneToMany(mappedBy = "role" , cascade = CascadeType.ALL , orphanRemoval = true)
	private Set<RolePermission> rolePermissions;
	
	
	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (!(o instanceof Role)) return false;
	    Role role = (Role) o;
	    return Objects.equals(getId(), role.getId());
	}

	@Override
	public int hashCode() {
	    return Objects.hash(getId());
	}

}
