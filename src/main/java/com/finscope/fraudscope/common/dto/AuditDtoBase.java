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
public class AuditDtoBase {

	private Long id;

	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	private String createdBy;

	private String updatedBy;

}
