package com.finscope.fraudscope.authorization.roleuser.dto;

import com.finscope.fraudscope.authorization.role.dto.DtoRole;
import com.finscope.fraudscope.user.dto.DtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRoleUser {

	private DtoRole role;

	private DtoUser user;
}
