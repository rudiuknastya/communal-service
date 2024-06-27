package com.example.chairman.model.voting;

import com.example.chairman.entity.enums.VotingResultStatus;
import com.example.chairman.entity.enums.VotingStatus;

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
