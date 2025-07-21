package com.finscope.fraudscope.authorization.role.entity;

import java.util.Objects;
import java.util.Set;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.authorization.rolepermission.entity.RolePermission;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "roles", indexes = { @Index(name = "idx_roles_deleted", columnList = "deleted") })
@SQLDelete(sql = "UPDATE roles SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class Role extends SoftDeletableAuditBase {

	@Column(unique = true, nullable = false, length = 64)
	private String name;

	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
	private Set<RolePermission> rolePermissions;

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof Role))
			return false;
		Role role = (Role) o;
		return Objects.equals(getId(), role.getId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId());
	}

}
