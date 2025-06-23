package com.finscope.fraudscope.authorization.rolepermission.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.finscope.fraudscope.authorization.permission.dto.DtoPermission;
import com.finscope.fraudscope.authorization.role.dto.DtoRole;
import com.finscope.fraudscope.common.dto.AuditDtoBase;
import com.finscope.fraudscope.common.enums.ApprovalStatus;
import com.finscope.fraudscope.user.dto.DtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DtoRolePermission extends AuditDtoBase {

	private DtoRole role;

	private DtoPermission permission;

	private DtoUser grantedBy;

	private LocalDateTime grantedAt;

	private boolean isActive;

	private ApprovalStatus approvalStatus;

	private DtoUser approvedBy;

	private LocalDateTime approvedAt;

	private String note;

}
