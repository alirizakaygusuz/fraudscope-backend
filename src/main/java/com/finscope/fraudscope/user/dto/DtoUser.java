package com.finscope.fraudscope.user.dto;

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

}
