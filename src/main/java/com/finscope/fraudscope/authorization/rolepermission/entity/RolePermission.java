package com.finscope.fraudscope.authorization.rolepermission.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.authentication.entity.AuthUser;
import com.finscope.fraudscope.authorization.permission.entity.Permission;
import com.finscope.fraudscope.authorization.role.entity.Role;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;
import com.finscope.fraudscope.common.enums.ApprovalStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
	    name = "role_permissions",
	    uniqueConstraints = { @UniqueConstraint(columnNames = { "role_id", "permission_id" }) },
	    indexes = {
	        @Index(name = "idx_transactions_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE role_permissions SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class RolePermission extends SoftDeletableAuditBase {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "permission_id", nullable = false)
	private Permission permission;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "granted_by", nullable = false)
	private AuthUser grantedBy;

	@Column(name = "granted_at", nullable = false)
	private LocalDateTime grantedAt;

	// Account is active or not
    @Builder.Default
	@Column(name = "is_active", nullable = false)
	private boolean isActive = false;

    @Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "approval_status", nullable = false)
	private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "approved_by", nullable = false)
	private AuthUser approvedBy;

	@Column(name = "approved_at")
	private LocalDateTime approvedAt;

	@Lob
	@Column(name = "note")
	@Size(min = 10)
	private String note;

}
