package com.finscope.fraudscope.account.dto;

import java.math.BigDecimal;

import com.finscope.fraudscope.common.dto.SoftDeletableAuditDtoBase;
import com.finscope.fraudscope.common.enums.AccountType;
import com.finscope.fraudscope.common.enums.CurrencyType;
import com.finscope.fraudscope.user.dto.DtoUser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAccount extends SoftDeletableAuditDtoBase {

	private String accountNumber;

	private CurrencyType currency;

	private AccountType accountType;

	private boolean active;

	private BigDecimal balance;

	private DtoUser user;

}
