package com.finscope.fraudscope.auth.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RolePermissionId implements Serializable {

	private Long roleId;
	private Long permissionId;
	@Override
	public int hashCode() {
		return Objects.hash(roleId , permissionId);
	}
	@Override
	public boolean equals(Object obj) {
		
		if(this == obj) {
			return true;
		}
		if(!(obj instanceof RolePermissionId)) {
			return false;
		}
		
		RolePermissionId rolePermissionId = (RolePermissionId)obj;
		
		return Objects.equals(roleId, rolePermissionId.roleId) && 
				Objects.equals(permissionId,rolePermissionId.permissionId);
		
	
	}
	
	
	
	
}
