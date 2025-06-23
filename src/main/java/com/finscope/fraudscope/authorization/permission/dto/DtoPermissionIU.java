package com.finscope.fraudscope.authorization.permission.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoPermissionIU {

	@NotBlank(message = "Permission name must not be blank")
	@Size(min = 2, max = 64, message = "Permission name must be between 2 and 64 characters")
	private String name;

	@NotBlank(message = "Permission description must not be blank")
	@Size(min = 10, max = 256, message = "Permission description must be between 2 and 64 characters")
	private String description;

}
