package com.finscope.fraudscope.authorization.role.dto;

import java.util.Set;

import com.finscope.fraudscope.authorization.rolepermission.dto.DtoRolePermission;
import com.finscope.fraudscope.common.dto.SoftDeletableAuditDtoBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DtoRole extends SoftDeletableAuditDtoBase {

	private String name;

	private Set<DtoRolePermission> rolePermissions;
}
