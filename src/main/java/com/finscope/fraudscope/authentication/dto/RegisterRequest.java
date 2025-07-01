package com.finscope.fraudscope.authentication.dto;

import jakarta.validation.constraints.Email;
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
public class RegisterRequest {

	@NotBlank(message = "Username cannot be blank")
	@Size(min = 2, max = 50)
	private String username;
	
	
	@NotBlank(message = "Email cannot be blank")
	@Email(message = "Email format is invalid")
	private String email;

    @NotBlank(message = "Password cannot be blank")
	@Size(min = 6)
	private String password;
    
    private String ipAddress;
    
    private String userAgent;
	

}
