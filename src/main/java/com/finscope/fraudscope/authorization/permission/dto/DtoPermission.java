package com.finscope.fraudscope.authorization.permission.dto;

import com.finscope.fraudscope.common.dto.SoftDeletableAuditDtoBase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoPermission extends SoftDeletableAuditDtoBase {

	private String name;

	private String description;

}
