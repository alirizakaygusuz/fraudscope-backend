package com.finscope.fraudscope.user.dto;

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
public class DtoUserIU {
	@NotBlank
	private String name;
	
	@NotBlank
	private String surname;

	@NotBlank
	private String phoneNumber;
}
