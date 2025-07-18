package com.finscope.fraudscope.user.enduser.dto;

import java.time.LocalDate;
import java.util.List;

import com.finscope.fraudscope.account.dto.DtoAccount;
import com.finscope.fraudscope.user.dto.DtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoEndUser extends DtoUser {
   
	private String address;
	
	private String country;
	
    private LocalDate dateOfBirth;
	
//    @JsonProperty("verified")
//	private boolean isVerified ;

	private List<DtoAccount> accounts;

}
