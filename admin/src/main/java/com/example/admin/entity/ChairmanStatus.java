package com.example.admin.entity;

public enum ChairmanStatus {
    ACTIVE("Активний"),
    DISABLED("Вимкнений");
    private final String statusName;

    ChairmanStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
