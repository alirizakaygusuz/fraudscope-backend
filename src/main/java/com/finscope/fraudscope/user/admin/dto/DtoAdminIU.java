package com.finscope.fraudscope.user.admin.dto;

import com.finscope.fraudscope.user.dto.DtoUserIU;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class DtoAdminIU extends DtoUserIU {

	@NotBlank
	private String title;

	@NotBlank
	private String registrationNumber;

}
