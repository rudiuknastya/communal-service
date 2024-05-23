package com.example.chairman.service;

import com.example.chairman.model.voting.FilterRequest;
import com.example.chairman.model.voting.TableVotingFormResponse;
import com.example.chairman.model.voting.ViewVotingFormResponse;
import com.example.chairman.model.voting.VotingFormDto;
import org.springframework.data.domain.Page;

public interface VotingService {
    void createVotingForm(VotingFormDto votingFormDto);
    VotingFormDto getVotingFormDto(Long id);
    void updateVotingForm(VotingFormDto votingFormDto, Long id);
    Page<TableVotingFormResponse> getVotingFormResponsesForTable(FilterRequest filterRequest);
    ViewVotingFormResponse getViewVotingFormResponse(Long id);
}
