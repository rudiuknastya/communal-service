package com.example.chairman.entity;

public enum UserVote {
    AGREE("За"),
    DISAGREE("Проти"),
    ABSTAIN("Утриматися");
    private final String statusName;

    UserVote(String statusName) {
        this.statusName = statusName;
    }

    public String getStatusName() {
        return statusName;
    }
}
