package com.example.admin.entity;

public enum HouseStatus {
    NEW("Новий"),
    ACTIVE("Активний"),
    DISABLED("Вимкнений");
    private final String statusName;

    HouseStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
