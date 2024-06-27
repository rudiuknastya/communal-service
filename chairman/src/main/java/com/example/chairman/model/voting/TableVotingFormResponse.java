package com.example.chairman.model.voting;

import com.example.chairman.entity.enums.VotingResultStatus;
import com.example.chairman.entity.enums.VotingStatus;

public record TableVotingFormResponse(
        Long id,
        String subject,
        String endDate,
        VotingStatus status,
        VotingResultStatus resultStatus,
        String voted
) {
}
