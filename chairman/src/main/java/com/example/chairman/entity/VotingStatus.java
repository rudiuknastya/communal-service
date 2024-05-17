package com.example.chairman.entity;

public enum VotingStatus {
    ACTIVE("Активне"),
    CLOSED("Закрито");
    private final String statusName;

    VotingStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
