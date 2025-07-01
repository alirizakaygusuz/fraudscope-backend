package com.finscope.fraudscope.user.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.finscope.fraudscope.account.dto.DtoAccount;
import com.finscope.fraudscope.authentication.dto.DtoAuthUser;
import com.finscope.fraudscope.common.dto.SoftDeletableAuditDtoBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUser extends SoftDeletableAuditDtoBase {
    
	private DtoAuthUser authUser;
	
	private String name;
	
	private String surname;
	
	private String phoneNumber;
	
	private String address;
	
	private String country;
	
    private LocalDate dateOfBirth;
	
    @JsonProperty("verified")
	private boolean isVerified ;

	private List<DtoAccount> accounts;

}
