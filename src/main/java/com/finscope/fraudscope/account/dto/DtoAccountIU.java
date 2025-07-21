package com.finscope.fraudscope.account.dto;

import java.math.BigDecimal;

import com.finscope.fraudscope.common.enums.AccountType;
import com.finscope.fraudscope.common.enums.CurrencyType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoAccountIU {

	@NotBlank(message = "Account numbber must not be blank")
	private String accountNumber;

	@NotNull(message = "Currency Type must not be null")
	private CurrencyType currency;

	@NotNull(message = "Account Type must not be null")
	private AccountType accountType;

	private boolean active = true;

	@NotNull(message = "Balance must not be null")
	@PositiveOrZero(message = "Balance cannot be negative")
	private BigDecimal balance;

	@NotNull(message = "User ID must not be null")
	@Positive(message = "User ID must be a positive number")
	private Long userId;

}
