package com.finscope.fraudscope.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Schema(description = "Request body for user register")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest implements ClientMetadataAware{

    @Schema(description = "Unique username for the new user", example = "AliRiza")
	@NotBlank(message = "Username cannot be blank")
	@Size(min = 2, max = 50)
	private String username;
	
    @Schema(description = "Valid email address of the new user", example = "aliriza@kaygusuz.com")
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email format is invalid")
	private String email;

    @Schema(description = "Password for the account", example = "P@ssw0rd123")
    @NotBlank(message = "Password cannot be blank")
	@Size(min = 6)
	private String password;
    
    private String ipAddress;
    
    private String userAgent;
	

}
