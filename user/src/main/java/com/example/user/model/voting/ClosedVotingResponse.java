package com.example.user.model.voting;

import com.example.user.entity.enums.UserVote;
import com.example.user.entity.enums.VotingResultStatus;
import com.example.user.entity.enums.VotingStatus;

import java.util.List;

public record ClosedVotingResponse(
        String subject,
        String text,
        String endDate,
        VotingStatus status,
        VotingResultStatus resultStatus,
        UserVote vote,
        List<Long> votesStatistic
) {
}
