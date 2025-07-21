package com.finscope.fraudscope.authorization.role.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class DtoRoleIU {

    @NotBlank(message = "Role name must not be blank")
    @Size(min = 2, max = 64, message = "Role name must be between 2 and 64 characters")
	private String name;

    @NotEmpty(message = "At least one permission must be assigned to the role")
    private Set<@Positive(message = "Permission ID must be a positive number") Long> permissionIds;
}
