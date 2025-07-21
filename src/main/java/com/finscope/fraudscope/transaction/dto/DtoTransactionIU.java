package com.finscope.fraudscope.transaction.dto;

import java.math.BigDecimal;

import com.finscope.fraudscope.common.enums.CurrencyType;
import com.finscope.fraudscope.common.enums.TransactionType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTransactionIU {

	@NotNull(message = "From account ID must not be null")
	private Long fromAccount;

	@NotNull(message = "To account ID must not be null")
	private Long toAccount;

	@NotNull(message = "Amount must not be null")
	@DecimalMin(value = "0.01", message = "Amount must be greater than 0")
	private BigDecimal amount;

	@NotNull(message = "Currency must not be null")
	private CurrencyType currency;

	@NotNull(message = "Transaction type must not be null")
	private TransactionType transactionType;
    
	private String description;



}
