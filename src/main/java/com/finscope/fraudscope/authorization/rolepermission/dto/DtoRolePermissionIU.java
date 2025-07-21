package com.finscope.fraudscope.authorization.rolepermission.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRolePermissionIU {

	@NotNull(message = "Role ID must not be null")
	@Positive(message = "Role ID must be a positive number")
	private Long roleId;

	@NotNull(message = "Permission ID must not be null")
	@Positive(message = "Permission ID must be a positive number")
	private Long permissionId;

	@Size(min = 10, message = "Note must be at least 10 characters")
	private String note;
}
