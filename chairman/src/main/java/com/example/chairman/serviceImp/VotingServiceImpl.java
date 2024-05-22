package com.example.chairman.serviceImp;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.mapper.VotingFormMapper;
import com.example.chairman.model.voting.VotingFormDto;
import com.example.chairman.repository.VotingFormRepository;
import com.example.chairman.service.VotingService;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class VotingServiceImpl implements VotingService {
    private final VotingFormRepository votingFormRepository;
    private final VotingFormMapper votingFormMapper;
    private final Logger logger = LogManager.getLogger(VotingServiceImpl.class);

    public VotingServiceImpl(VotingFormRepository votingFormRepository, VotingFormMapper votingFormMapper) {
        this.votingFormRepository = votingFormRepository;
        this.votingFormMapper = votingFormMapper;
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

    private VotingForm getVotingForm(Long id) {
        return votingFormRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Voting form was not found by id "+id));
    }
}
