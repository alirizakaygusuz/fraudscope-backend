package com.finscope.fraudscope.common.audit;

import java.time.LocalDateTime;


import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public abstract class SoftDeletableAuditBase extends AuditBase {
	
    @Builder.Default
	@Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	@NotNull(message = "Deleted flag cannot be null")
	private Boolean deleted = false;
	

	@Column(name = "delete_by")
	String deletedBy;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

}
