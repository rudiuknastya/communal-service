package com.example.admin.entity;

public enum UserStatus {
    NEW("Новий"),
    ACTIVE("Активний"),
    DISABLED("Вимкнений");
    private final String statusName;

    UserStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
