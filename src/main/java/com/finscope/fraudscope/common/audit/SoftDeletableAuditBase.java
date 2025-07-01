package com.finscope.fraudscope.common.audit;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@Getter
@Setter
public class SoftDeletableAuditBase extends AuditBase {
	
	
	@Column(nullable = false)
	@NotNull(message = "Deleted flag cannot be null")
	private Boolean deleted = false;
	

	@Column(name = "delete_by")
	String deletedBy;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

}
