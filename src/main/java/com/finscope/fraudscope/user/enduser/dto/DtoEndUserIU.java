package com.finscope.fraudscope.user.enduser.dto;

import java.time.LocalDate;

import com.finscope.fraudscope.user.dto.DtoUserIU;

import jakarta.validation.constraints.Past;
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
public class DtoEndUserIU extends DtoUserIU{

	private String address;

	private String country;

	@Past
	private LocalDate dateOfBirth;
	
}
