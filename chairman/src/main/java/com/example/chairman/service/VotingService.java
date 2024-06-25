package com.example.chairman.service;

import com.example.chairman.model.voting.*;
import org.springframework.data.domain.Page;

public interface VotingService {
    void createVotingForm(VotingFormDto votingFormDto);
    VotingFormDto getVotingFormDto(Long id);
    void updateVotingForm(VotingFormDto votingFormDto, Long id);
    Page<TableVotingFormResponse> getVotingFormResponsesForTable(FilterRequest filterRequest);
    ViewVotingFormResponse getViewVotingFormResponse(Long id);
    Page<VotedUserResponse> getVotedUserResponsesForTable(Long id, UsersFilterRequest usersFilterRequest);
    void deleteVotingForm(Long id);
    void closeVoting(Long votingFormId);
}
