package com.example.user.model.voting;

import com.example.user.entity.enums.UserVote;

public record ActiveVotingResponse(
        String subject,
        String text,
        String endDate,
        Long totalVotes,
        UserVote vote
) {
}
