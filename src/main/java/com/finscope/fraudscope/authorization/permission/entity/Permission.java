package com.finscope.fraudscope.authorization.permission.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
	    name = "permissions",
	    indexes = {
	        @Index(name = "idx_permissions_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE permissions SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class Permission extends SoftDeletableAuditBase {

	@Column(unique = true, nullable = false, length = 64)
	private String name;

	@Column(nullable = false, length = 256)
	private String description;

}
