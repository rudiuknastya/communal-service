package com.example.user.service;

import com.example.user.entity.enums.UserVote;
import com.example.user.model.voting.ActiveVotingResponse;
import com.example.user.model.voting.ClosedVotingResponse;
import com.example.user.model.voting.FilterRequest;
import com.example.user.model.voting.TableVotingFormResponse;
import org.springframework.data.domain.Page;

public interface VotingService {
    Page<TableVotingFormResponse> getVotingFormResponsesForTable(FilterRequest filterRequest);
    ActiveVotingResponse getActiveVotingResponse(Long id);
    void updateVote(Long id, UserVote userVote);
    ClosedVotingResponse getClosedVotingResponse(Long id);
}
