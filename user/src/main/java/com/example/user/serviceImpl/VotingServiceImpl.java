package com.example.user.serviceImpl;

import com.example.user.entity.*;
import com.example.user.mapper.VotingFormMapper;
import com.example.user.model.voting.ActiveVotingResponse;
import com.example.user.model.voting.ClosedVotingResponse;
import com.example.user.model.voting.FilterRequest;
import com.example.user.model.voting.TableVotingFormResponse;
import com.example.user.repository.UserRepository;
import com.example.user.repository.VoteRepository;
import com.example.user.repository.VotingFormRepository;
import com.example.user.service.VotingService;
import com.example.user.specification.specificationFormer.VotingFormSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VotingServiceImpl implements VotingService {
    private final VotingFormRepository votingFormRepository;
    private final VoteRepository voteRepository;
    private final UserRepository userRepository;
    private final VotingFormMapper votingFormMapper;
    private final Logger logger = LogManager.getLogger(VotingServiceImpl.class);

    public VotingServiceImpl(VotingFormRepository votingFormRepository, VoteRepository voteRepository,
                             UserRepository userRepository, VotingFormMapper votingFormMapper) {
        this.votingFormRepository = votingFormRepository;
        this.voteRepository = voteRepository;
        this.userRepository = userRepository;
        this.votingFormMapper = votingFormMapper;
    }

    @Override
    public Page<TableVotingFormResponse> getVotingFormResponsesForTable(FilterRequest filterRequest) {
        logger.info("getVotingFormResponsesForTable - Getting voting form responses for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<VotingForm> votingForms = getFilteredVotingForms(filterRequest, pageable);
        List<TableVotingFormResponse> tableVotingFormResponses = getTableVotingFormResponses(votingForms);
        Page<TableVotingFormResponse> tableVotingFormResponsePage = new PageImpl<>(tableVotingFormResponses, pageable, votingForms.getTotalElements());
        logger.info("getVotingFormResponsesForTable - Voting form responses have been got");
        return tableVotingFormResponsePage;
    }
    private List<TableVotingFormResponse> getTableVotingFormResponses(Page<VotingForm> votingForms) {
        List<TableVotingFormResponse> tableVotingFormResponses = new ArrayList<>();
        for(VotingForm votingForm: votingForms.getContent()){
            TableVotingFormResponse tableVotingFormResponse;
            if(votingForm.getStatus().equals(VotingStatus.ACTIVE)){
                Long votesCount = voteRepository.getVotesCountByVotingFormId(votingForm.getId());
                tableVotingFormResponse = votingFormMapper
                        .votingFormToTableVotingFormResponse(votingForm,votesCount.toString());
            } else {
                String voted = getVoted(votingForm.getId());
                tableVotingFormResponse = votingFormMapper
                        .votingFormToTableVotingFormResponse(votingForm, voted);
            }
            tableVotingFormResponses.add(tableVotingFormResponse);
        }
        return tableVotingFormResponses;
    }
    String getVoted(Long id){
        Long agreeVotesCount = voteRepository.getAgreeVoteCountByVotingFormId(id);
        Long disagreeVotesCount = voteRepository.getDisagreeVoteCountByVotingFormId(id);
        Long abstainVotesCount = voteRepository.getAbstainVoteCountByVotingFormId(id);
        String voted = agreeVotesCount+"/"+abstainVotesCount+"/"+disagreeVotesCount;
        return voted;
    }

    private Page<VotingForm> getFilteredVotingForms(FilterRequest filterRequest, Pageable pageable) {
        Specification<VotingForm> votingFormSpecification = VotingFormSpecificationFormer
                .formSpecification(filterRequest);
        return votingFormRepository.findAll(votingFormSpecification, pageable);
    }

    @Override
    public ActiveVotingResponse getActiveVotingResponse(Long id) {
        logger.info("getActiveVotingResponse - Getting active voting response by voting form id "+id);
        VotingForm votingForm = getVotingFormById(id);
        Long totalVotes = voteRepository.getVotesCountByVotingFormId(id);
        UserVote userVote = getUserVote(id);
        ActiveVotingResponse activeVotingResponse = votingFormMapper.votingToActiveVotingResponse(votingForm, totalVotes, userVote);
        logger.info("getActiveVotingResponse - Active voting response has been got");
        return activeVotingResponse;
    }

    @Override
    public void updateVote(Long id, UserVote userVote) {
        String username = getAuthenticatedUserUsername();
        Optional<Vote> voteOptional = voteRepository.findByVotingFormIdAndUserUsername(id, username);
        if(voteOptional.isEmpty()){
            VotingForm votingForm = getVotingFormById(id);
            User user = userRepository.findByUsernameAndDeletedIsFalse(username).orElseThrow(()-> new EntityNotFoundException("User was not found by username "+username));
            Vote vote = votingFormMapper.createVote(votingForm, user, userVote);
            voteRepository.save(vote);
        } else {
            Vote vote = voteOptional.get();
            vote.setUserVote(userVote);
            voteRepository.save(vote);
        }
    }

    @Override
    public ClosedVotingResponse getClosedVotingResponse(Long id) {
        logger.info("getClosedVotingResponse - Getting closed voting response by voting form id "+id);
        VotingForm votingForm = getVotingFormById(id);
        UserVote userVote = getUserVote(id);
        List<Long> votesStatistic = getVotesStatistic(id);
        ClosedVotingResponse closedVotingResponse = votingFormMapper
                .createClosedVotingResponse(votingForm, userVote, votesStatistic);
        logger.info("getClosedVotingResponse - Closed voting response has been got");
        return closedVotingResponse;
    }
    List<Long> getVotesStatistic(Long votingFormId){
        Long agreeVotesCount = voteRepository.getAgreeVoteCountByVotingFormId(votingFormId);
        Long disagreeVotesCount = voteRepository.getDisagreeVoteCountByVotingFormId(votingFormId);
        Long abstainVotesCount = voteRepository.getAbstainVoteCountByVotingFormId(votingFormId);
        return List.of(agreeVotesCount, disagreeVotesCount, abstainVotesCount);
    }

    UserVote getUserVote(Long votingFormId){
        String username = getAuthenticatedUserUsername();
        Optional<Vote> voteOptional = voteRepository.findByVotingFormIdAndUserUsername(votingFormId, username);
        UserVote userVote = null;
        if(voteOptional.isPresent()){
            userVote = voteOptional.get().getUserVote();
        }
        return userVote;
    }
    private VotingForm getVotingFormById(Long id) {
        return votingFormRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Voting form was not found by id "+id));
    }

    private String getAuthenticatedUserUsername() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails.getUsername();
    }
}
