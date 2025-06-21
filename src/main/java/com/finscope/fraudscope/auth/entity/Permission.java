package com.finscope.fraudscope.auth.entity;

import com.finscope.fraudscope.common.audit.AuditBase;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "permissons")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Permission extends AuditBase  {
	
	@Column(unique = true, nullable = false , length = 64)
	private String name;
	
	@Column(nullable = false , length = 256)
	private String description;

}
