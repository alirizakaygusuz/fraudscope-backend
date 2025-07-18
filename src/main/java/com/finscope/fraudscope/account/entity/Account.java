package com.finscope.fraudscope.account.entity;

import java.math.BigDecimal;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;
import com.finscope.fraudscope.common.enums.AccountType;
import com.finscope.fraudscope.common.enums.CurrencyType;
import com.finscope.fraudscope.user.enduser.entity.EndUser;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(
	    name = "accounts",
	    indexes = {
	        @Index(name = "idx_accounts_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE accounts SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor(force = true)
public class Account extends SoftDeletableAuditBase {

	@Column(nullable = false, unique = true, length = 34)
	private String accountNumber;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CurrencyType currency;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private AccountType accountType;

    @Builder.Default
	@Column(nullable = false, columnDefinition ="BOOLEAN DEFAULT TRUE")
	private boolean active = true;

	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal balance;

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "user_id", nullable = false)
	private EndUser user;

	
	public void setId(Long id) {
		super.id = id;
	}
}
