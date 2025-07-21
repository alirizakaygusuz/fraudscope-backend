package com.finscope.fraudscope.common.enums;

public enum TransactionType implements BaseEnum {

    TRANSFER("transaction.transfer"),
    DEPOSIT("transaction.deposit"),
    WITHDRAWAL("transaction.withdrawal"),
    PAYMENT("transaction.payment"),
    CHARGE("transaction.charge"),
    REFUND("transaction.refund");

    private final String i18n;

    TransactionType(String i18n) {
        this.i18n = i18n;
    }

    @Override
    public String getCode() {
        return i18n;
    }
}
