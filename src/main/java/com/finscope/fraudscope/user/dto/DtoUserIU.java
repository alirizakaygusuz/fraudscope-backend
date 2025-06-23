package com.finscope.fraudscope.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserIU {

	@NotBlank(message = "Username cannot be blank")
	@Size(min = 2, max = 50)
	private String username;

	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email format is invalid")
	private String email;

	@NotBlank(message = "Password cannot be blank")
	/*
	 * (?=.*[a-z]) // At least one lowercase letter 
	 * (?=.*[A-Z]) // At least one uppercase letter 
	 * (?=.*\\d)   // At least one digit (0–9) 
	 * (?=.*[@$!%*?&]) // At least one special character (@, $, !, %, *, ?, &) 
	 * [A-Za-z\\d@$!%*?&] //Only these characters allowed
	 * {6,}  // minimum length 6
	 */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "Password must contain at least 6 characters, including uppercase, lowercase, number, and special character")
	private String password;

	private boolean isEnabled;

}
