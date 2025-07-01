package com.finscope.fraudscope.user.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoUserIU {

	private String name;

	private String surname;

	private String phoneNumber;

	private String address;

	private String country;

	private LocalDate dateOfBirth;
	
}
