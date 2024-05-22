package com.example.chairman.service;

import com.example.chairman.model.voting.VotingFormDto;

public interface VotingService {
    void createVotingForm(VotingFormDto votingFormDto);
    VotingFormDto getVotingFormDto(Long id);
    void updateVotingForm(VotingFormDto votingFormDto, Long id);
}
