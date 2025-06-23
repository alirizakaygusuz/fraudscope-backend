package com.finscope.fraudscope.authorization.roleuser.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoRoleUserIU {

	@NotNull(message = "Role ID must not be null")
	@Positive(message = "Role ID must be a positive number")
	private Long roleId;
	
	@NotNull(message = "User ID must not be null")
	@Positive(message = "User ID must be a positive number")
	private Long userId;
}
