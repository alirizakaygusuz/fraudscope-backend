package com.finscope.fraudscope.common.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class SoftDeletableAuditDtoBase extends AuditDtoBase {

	private Boolean deleted = false;

	private String deletedBy;

	private LocalDateTime deletedAt;
}
