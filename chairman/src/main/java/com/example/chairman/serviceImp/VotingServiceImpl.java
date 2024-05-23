package com.example.chairman.serviceImp;

import com.example.chairman.entity.Vote;
import com.example.chairman.entity.VotingForm;
import com.example.chairman.entity.VotingStatus;
import com.example.chairman.mapper.VotingFormMapper;
import com.example.chairman.model.voting.*;
import com.example.chairman.repository.VoteRepository;
import com.example.chairman.repository.VotingFormRepository;
import com.example.chairman.service.VotingService;
import com.example.chairman.specification.VoteSpecification;
import com.example.chairman.specification.specificationFormer.VoteSpecificationFormer;
import com.example.chairman.specification.specificationFormer.VotingFormSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class VotingServiceImpl implements VotingService {
    private final VotingFormRepository votingFormRepository;
    private final VotingFormMapper votingFormMapper;
    private final VoteRepository voteRepository;
    private final Logger logger = LogManager.getLogger(VotingServiceImpl.class);

    public VotingServiceImpl(VotingFormRepository votingFormRepository, VotingFormMapper votingFormMapper,
                             VoteRepository voteRepository) {
        this.votingFormRepository = votingFormRepository;
        this.votingFormMapper = votingFormMapper;
        this.voteRepository = voteRepository;
    }

    @Override
    public void createVotingForm(VotingFormDto votingFormDto) {
        logger.info("createVotingForm - Creating voting form "+ votingFormDto.toString());
        VotingForm votingForm = votingFormMapper.votingFormDtoToVotingForm(votingFormDto);
        votingFormRepository.save(votingForm);
        logger.info("createVotingForm - Voting form has been created");
    }

    @Override
    public VotingFormDto getVotingFormDto(Long id) {
        logger.info("getVotingFormDto - Getting voting form dto by id "+id);
        VotingForm votingForm = getVotingForm(id);
        VotingFormDto votingFormDto = votingFormMapper.votingFormToVotingFormDto(votingForm);
        logger.info("getVotingFormDto - Voting form dto has been got");
        return votingFormDto;
    }

    @Override
    public void updateVotingForm(VotingFormDto votingFormDto, Long id) {
        logger.info("updateVotingForm - Updating voting form by id "+id+" "+votingFormDto.toString());
        VotingForm votingForm = getVotingForm(id);
        votingFormMapper.updateVotingForm(votingForm, votingFormDto);
        votingFormRepository.save(votingForm);
        logger.info("updateVotingForm - Voting form has been updated");
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
                Long agreeVotesCount = voteRepository.getAgreeVoteCountByVotingFormId(votingForm.getId());
                Long disagreeVotesCount = voteRepository.getDisagreeVoteCountByVotingFormId(votingForm.getId());
                Long abstainVotesCount = voteRepository.getAbstainVoteCountByVotingFormId(votingForm.getId());
                String voted = agreeVotesCount+"/"+abstainVotesCount+"/"+disagreeVotesCount;
                tableVotingFormResponse = votingFormMapper
                        .votingFormToTableVotingFormResponse(votingForm, voted);
            }
            tableVotingFormResponses.add(tableVotingFormResponse);
        }
        return tableVotingFormResponses;
    }

    private Page<VotingForm> getFilteredVotingForms(FilterRequest filterRequest, Pageable pageable) {
        Specification<VotingForm> votingFormSpecification = VotingFormSpecificationFormer
                .formSpecification(filterRequest);
        return votingFormRepository.findAll(votingFormSpecification, pageable);
    }

    @Override
    public ViewVotingFormResponse getViewVotingFormResponse(Long id) {
        logger.info("getViewVotingFormResponse - Getting view voting form response by id "+id);
        VotingForm votingForm = getVotingForm(id);
        Long voted = voteRepository.getVotesCountByVotingFormId(votingForm.getId());
        Long agreeVotesCount = voteRepository.getAgreeVoteCountByVotingFormId(votingForm.getId());
        Long disagreeVotesCount = voteRepository.getDisagreeVoteCountByVotingFormId(votingForm.getId());
        Long abstainVotesCount = voteRepository.getAbstainVoteCountByVotingFormId(votingForm.getId());
        ViewVotingFormResponse votingFormResponse = votingFormMapper
                .votingFormToViewVotingFormResponse(votingForm, voted,
                        List.of(agreeVotesCount, disagreeVotesCount, abstainVotesCount));
        logger.info("getViewVotingFormResponse - View voting form response has been got");
        return votingFormResponse;
    }

    @Override
    public Page<VotedUserResponse> getVotedUserResponsesForTable(Long id, UsersFilterRequest usersFilterRequest) {
        logger.info("getVotedUserResponsesForTable - Getting voted user responses for table by id "+id+" "+usersFilterRequest.toString());
        Pageable pageable = PageRequest.of(usersFilterRequest.page(), usersFilterRequest.pageSize());
        Page<Vote> votes = getFilteredVotes(id, usersFilterRequest, pageable);
        List<VotedUserResponse> votedUserResponses = votingFormMapper
                .voteListToVotedUserResponseList(votes.getContent());
        Page<VotedUserResponse> votedUserResponsePage = new PageImpl<>(votedUserResponses, pageable, votes.getTotalElements());
        logger.info("getVotedUserResponsesForTable - Voted user responses have been got");
        return votedUserResponsePage;
    }

    private Page<Vote> getFilteredVotes(Long id, UsersFilterRequest usersFilterRequest, Pageable pageable) {
        Specification<Vote> voteSpecification = VoteSpecificationFormer.formSpecification(id, usersFilterRequest);
        return voteRepository.findAll(voteSpecification, pageable);
    }

    private VotingForm getVotingForm(Long id) {
        return votingFormRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Voting form was not found by id "+id));
    }
}
