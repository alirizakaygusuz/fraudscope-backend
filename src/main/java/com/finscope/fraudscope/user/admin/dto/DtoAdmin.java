package com.finscope.fraudscope.user.admin.dto;

import com.finscope.fraudscope.user.dto.DtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAdmin extends DtoUser{

	private String title;

	private String registrationNumber;

}
