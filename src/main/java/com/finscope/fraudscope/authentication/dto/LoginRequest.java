package com.finscope.fraudscope.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Request body for user register")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements ClientMetadataAware {

    @Schema(description = "Email or username used to log in", example = "john_doe")
	@NotBlank(message = "Email or Username cannot be blank")
	private String emailOrUsername;

    
    @Schema(description = "Password for authentication", example = "P@ssw0rd123")
	@NotBlank(message = "Password cannot be blank")
	/*
	 * (?=.*[a-z]) // At least one lowercase letter (?=.*[A-Z]) // At least one
	 * uppercase letter (?=.*\\d) // At least one digit (0–9) (?=.*[@$!%*?&]) // At
	 * least one special character (@, $, !, %, *, ?, &) [A-Za-z\\d@$!%*?&] //Only
	 * these characters allowed {6,} // minimum length 6
	 */
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "Password must contain at least 6 characters, including uppercase, lowercase, number, and special character")
	private String password;

	private String ipAddress;

	private String userAgent;

}
