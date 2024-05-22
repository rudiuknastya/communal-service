package com.example.chairman.serviceImp;

import com.example.chairman.entity.VotingForm;
import com.example.chairman.mapper.VotingFormMapper;
import com.example.chairman.model.voting.VotingFormDto;
import com.example.chairman.repository.VotingFormRepository;
import com.example.chairman.service.VotingService;
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
        logger.info("updateUser - Creating voting form "+votingFormDto.toString());
        VotingForm votingForm = votingFormMapper.votingFormDtoToVotingForm(votingFormDto);
        votingFormRepository.save(votingForm);
        logger.info("updateUser - Voting form has been created");
    }
}
