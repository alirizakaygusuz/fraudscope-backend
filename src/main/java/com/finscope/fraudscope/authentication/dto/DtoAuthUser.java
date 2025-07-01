package com.finscope.fraudscope.authentication.dto;

import java.util.Set;

import com.finscope.fraudscope.authorization.roleuser.dto.DtoRoleUser;
import com.finscope.fraudscope.common.dto.SoftDeletableAuditDtoBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAuthUser extends SoftDeletableAuditDtoBase {

	private String username;

	private String email;

	private boolean isEnabled ;

	private Set<DtoRoleUser> userRoles;
	

}
