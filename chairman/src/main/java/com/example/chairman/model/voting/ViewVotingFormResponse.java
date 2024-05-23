package com.example.chairman.model.voting;

import com.example.chairman.entity.VotingResultStatus;
import com.example.chairman.entity.VotingStatus;

import java.time.LocalDateTime;
import java.util.List;

public record ViewVotingFormResponse(
        String subject,
        String text,
        LocalDateTime startDate,
        LocalDateTime endDate,
        VotingStatus status,
        VotingResultStatus resultStatus,
        Long voted,
        List<Long> votesStatistic
) {
}
