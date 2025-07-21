package com.finscope.fraudscope.common.enums;

public enum ApprovalStatus implements BaseEnum{
	PENDING("approval.pending"),
    APPROVED("approval.approved"),
    REJECTED("approval.rejected");

    private final String i18n;

    private ApprovalStatus(String code) {
        this.i18n = code;
    }

    @Override
    public String getCode() {
        return i18n;
    }
}
