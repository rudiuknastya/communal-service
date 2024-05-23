package com.example.chairman.model.voting;

import com.example.chairman.entity.UserVote;

import java.math.BigDecimal;

public record VotedUserResponse(
        String fullName,
        Long apartmentNumber,
        BigDecimal area,
        String phoneNumber,
        UserVote userVote
) {
}
