package com.finscope.fraudscope.account.entity;

import java.math.BigDecimal;

import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;
import com.finscope.fraudscope.common.enums.AccountType;
import com.finscope.fraudscope.common.enums.CurrencyType;
import com.finscope.fraudscope.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "accounts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account extends SoftDeletableAuditBase{

	@Column(nullable = false, unique = true, length = 34)
	private String accountNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private CurrencyType currency;
	
	private AccountType accountType;
	
	
	@Column(nullable = false)
	private boolean active= true;
	
	@Column(nullable = false, precision = 19, scale = 4)
	private BigDecimal balance;
	
	@ManyToOne(fetch = FetchType.LAZY , cascade = {CascadeType.PERSIST , CascadeType.MERGE} )
	@JoinColumn(name = "user_id" ,nullable = false)
	private User user;
	
	
}
