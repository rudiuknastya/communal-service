package com.example.chairman.model.voting;

import com.example.chairman.entity.VotingResultStatus;
import com.example.chairman.entity.VotingStatus;

import java.time.LocalDate;
public record FilterRequest(
        int page,
        int pageSize,
        String subject,
        LocalDate endDate,
        VotingStatus status,
        VotingResultStatus resultStatus
) {
}
