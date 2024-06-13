package com.example.user.model.voting;


import com.example.user.entity.VotingResultStatus;
import com.example.user.entity.VotingStatus;

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
