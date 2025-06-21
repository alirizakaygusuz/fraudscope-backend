package com.finscope.fraudscope.auth.entity;

import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class RoleUserId {
	
	private Long roleId;
	private Long userId;


	@Override
	public int hashCode() {
		
		return Objects.hash(roleId , userId);
	}


	@Override
	public boolean equals(Object obj) {
		if(this == obj) {
			return true;
		}
		
		if(!(obj  instanceof RoleUserId)) {
			return false;
		}
		
		RoleUserId roleUserId = (RoleUserId) obj;
		
		
		return Objects.equals(roleId, roleUserId.roleId) &&
				Objects.equals(userId, roleUserId.userId);
	}
	
	
	

}
