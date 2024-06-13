package com.example.user.model.voting;


import com.example.user.entity.VotingResultStatus;
import com.example.user.entity.VotingStatus;

public record TableVotingFormResponse(
        Long id,
        String subject,
        String endDate,
        VotingStatus status,
        VotingResultStatus resultStatus,
        String voted
) {
}
