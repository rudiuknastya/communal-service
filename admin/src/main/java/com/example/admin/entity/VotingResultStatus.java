package com.example.admin.entity;

public enum VotingResultStatus {
    ACCEPTED("Прийнято"),
    REJECTED("Відхилено");
    private final String statusName;

    VotingResultStatus(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
