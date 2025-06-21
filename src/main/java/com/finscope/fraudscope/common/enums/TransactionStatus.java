package com.finscope.fraudscope.common.enums;

public enum TransactionStatus implements BaseEnum {

    PENDING("transaction.status.pending"),
    APPROVED("transaction.status.approved"),
    REJECTED("transaction.status.rejected"),
    COMPLETED("transaction.status.completed"),
    FAILED("transaction.status.failed");

    private final String i18n;

    TransactionStatus(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String getCode() {
        return i18n;
    }
}
