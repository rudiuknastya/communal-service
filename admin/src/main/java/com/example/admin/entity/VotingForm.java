package com.example.admin.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "voting_forms")
public class VotingForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100, nullable = false)
    private String subject;
    @Column(length = 400, nullable = false)
    private String text;
    @Column(nullable = false)
    private LocalDateTime startDate;
    @Column(nullable = false)
    private LocalDateTime endDate;
    @Column(nullable = false)
    private Long quorum;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotingStatus status;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private VotingResultStatus resultStatus;
    private boolean deleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Long getQuorum() {
        return quorum;
    }

    public void setQuorum(Long quorum) {
        this.quorum = quorum;
    }

    public VotingStatus getStatus() {
        return status;
    }

    public void setStatus(VotingStatus status) {
        this.status = status;
    }

    public VotingResultStatus getResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(VotingResultStatus resultStatus) {
        this.resultStatus = resultStatus;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
