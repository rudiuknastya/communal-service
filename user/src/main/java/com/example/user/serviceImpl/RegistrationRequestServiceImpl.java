package com.example.user.serviceImpl;

import com.example.user.entity.Chairman;
import com.example.user.entity.RegistrationRequest;
import com.example.user.entity.User;
import com.example.user.entity.enums.RequestStatus;
import com.example.user.mapper.RegistrationRequestMapper;
import com.example.user.repository.RegistrationRequestRepository;
import com.example.user.service.RegistrationRequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {
    private final RegistrationRequestRepository registrationRequestRepository;
    private final RegistrationRequestMapper registrationRequestMapper;
    private final Logger logger = LogManager.getLogger(RegistrationRequestServiceImpl.class);

    public RegistrationRequestServiceImpl(RegistrationRequestRepository registrationRequestRepository,
                                          RegistrationRequestMapper registrationRequestMapper) {
        this.registrationRequestRepository = registrationRequestRepository;
        this.registrationRequestMapper = registrationRequestMapper;
    }

    @Override
    public void createRegistrationRequest(User user, Chairman chairman) {
        logger.info("createRegistrationRequest - Creating registration request with user id "+user.getId()+" with chairman id "+chairman.getId());
        RegistrationRequest registrationRequest = registrationRequestMapper
                .createRegistrationRequest(RequestStatus.IN_PROCESS, user, chairman);
        registrationRequestRepository.save(registrationRequest);
        logger.info("createRegistrationRequest - Registration request has been created");
    }
}
