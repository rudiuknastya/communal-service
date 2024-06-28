package com.example.user.model.voting;


import com.example.user.entity.enums.VotingResultStatus;
import com.example.user.entity.enums.VotingStatus;

public record TableVotingFormResponse(
        Long id,
        String subject,
        String endDate,
        VotingStatus status,
        VotingResultStatus resultStatus,
        String voted
) {
}
