package com.finscope.fraudscope.transaction.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.finscope.fraudscope.account.entity.Account;
import com.finscope.fraudscope.common.audit.SoftDeletableAuditBase;
import com.finscope.fraudscope.common.enums.CurrencyType;
import com.finscope.fraudscope.common.enums.TransactionStatus;
import com.finscope.fraudscope.common.enums.TransactionType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(
	    name = "transactions",
	    indexes = {
	        @Index(name = "idx_transactions_deleted", columnList = "deleted")}
	  )
@SQLDelete(sql = "UPDATE transactions SET deleted = true WHERE id = ?")
@SQLRestriction("deleted = false")
public class Transaction extends SoftDeletableAuditBase {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "from_account_id", nullable = false)
    @NotNull(message = "From account must not be null")
    private Account fromAccount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "to_account_id", nullable = false)
    @NotNull(message = "To account must not be null")
    private Account toAccount;

    @Column(nullable = false, precision = 19, scale = 4)
    @NotNull(message = "Amount must not be null")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Currency must not be null")
    private CurrencyType currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Transaction type must not be null")
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Transaction status must not be null")
    private TransactionStatus transactionStatus = TransactionStatus.PENDING;

    @Column(length = 255)
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Initiation timestamp must not be null")
    private LocalDateTime transactionInitiatedAt = LocalDateTime.now();

    private LocalDateTime transactionCompletedAt;

    private LocalDateTime statusUpdatedAt;
}
