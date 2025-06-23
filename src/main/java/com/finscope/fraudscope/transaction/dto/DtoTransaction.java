package com.finscope.fraudscope.transaction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.finscope.fraudscope.account.dto.DtoAccount;
import com.finscope.fraudscope.common.enums.CurrencyType;
import com.finscope.fraudscope.common.enums.TransactionStatus;
import com.finscope.fraudscope.common.enums.TransactionType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DtoTransaction {
	 
	    private DtoAccount fromAccount;

	    
	    private DtoAccount toAccount;

	  
	    private BigDecimal amount;

	   
	    private CurrencyType currency;

	   
	    private TransactionType transactionType;

	   
	    private TransactionStatus transactionStatus;

	    
	    private String description;

	   
	    private LocalDateTime transactionInitiatedAt;

	    private LocalDateTime transactionCompletedAt;

	    private LocalDateTime statusUpdatedAt;
	

}
