package com.finscope.fraudscope.user.dto;

import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finscope.fraudscope.account.dto.DtoAccount;
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
public class DtoUser extends SoftDeletableAuditDtoBase {

	private String username;

	private String email;

    @JsonProperty("enabled")
	private boolean isEnabled;

	private List<DtoAccount> accounts;

	private Set<DtoRoleUser> userRoles;
}
