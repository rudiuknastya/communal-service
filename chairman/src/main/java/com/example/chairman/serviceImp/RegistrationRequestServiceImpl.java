package com.example.chairman.serviceImp;

import com.example.chairman.entity.RegistrationRequest;
import com.example.chairman.entity.enums.RequestStatus;
import com.example.chairman.entity.enums.UserStatus;
import com.example.chairman.mapper.RegistrationRequestMapper;
import com.example.chairman.model.registrationRequest.FilterRequest;
import com.example.chairman.model.registrationRequest.RegistrationRequestResponse;
import com.example.chairman.model.registrationRequest.TableRegistrationRequestResponse;
import com.example.chairman.repository.RegistrationRequestRepository;
import com.example.chairman.repository.UserRepository;
import com.example.chairman.service.RegistrationRequestService;
import com.example.chairman.specification.specificationFormer.RegistrationRequestSpecificationFormer;
import jakarta.persistence.EntityNotFoundException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
@Service
public class RegistrationRequestServiceImpl implements RegistrationRequestService {
    private final RegistrationRequestRepository registrationRequestRepository;
    private final RegistrationRequestMapper registrationRequestMapper;
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(RegistrationRequestServiceImpl.class);

    public RegistrationRequestServiceImpl(RegistrationRequestRepository registrationRequestRepository,
                                          RegistrationRequestMapper registrationRequestMapper,
                                          UserRepository userRepository) {
        this.registrationRequestRepository = registrationRequestRepository;
        this.registrationRequestMapper = registrationRequestMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Page<TableRegistrationRequestResponse> getRegistrationRequestsForTable(FilterRequest filterRequest) {
        logger.info("getRegistrationRequestsForTable - Getting registration requests for table "+filterRequest.toString());
        Pageable pageable = PageRequest.of(filterRequest.page(), filterRequest.pageSize());
        Page<RegistrationRequest> registrationRequestPage = getFilteredRegistrationRequests(pageable, filterRequest);
        List<TableRegistrationRequestResponse> tableRegistrationRequestResponses = registrationRequestMapper
                .registrationRequestListToTableRegistrationRequestResponseList(registrationRequestPage.getContent());
        Page<TableRegistrationRequestResponse> tableRegistrationRequestResponsePage = new PageImpl<>(tableRegistrationRequestResponses, pageable, registrationRequestPage.getTotalElements());
        logger.info("getRegistrationRequestsForTable - Registration requests for table have been got");
        return tableRegistrationRequestResponsePage;
    }

    private Page<RegistrationRequest> getFilteredRegistrationRequests(Pageable pageable, FilterRequest filterRequest) {
        Specification<RegistrationRequest> registrationRequestSpecification = RegistrationRequestSpecificationFormer.formTableSpecification(filterRequest);
        return registrationRequestRepository.findAll(registrationRequestSpecification, pageable);
    }

    @Override
    public void deleteRequestsByIds(Long[] ids) {
        logger.info("deleteRequestsByIds - Deleting registration requests by ids "+ids.toString());
        List<RegistrationRequest> registrationRequests = registrationRequestRepository.findAllById(Arrays.asList(ids));
        for(RegistrationRequest registrationRequest: registrationRequests){
            registrationRequest.setDeleted(true);
        }
        registrationRequestRepository.saveAll(registrationRequests);
        logger.info("deleteRequestsByIds - Registration requests have been deleted");
    }

    @Override
    public RegistrationRequestResponse getRegistrationRequest(Long id) {
        logger.info("getRegistrationRequest - Getting registration request by id "+id);
        RegistrationRequest registrationRequest = getRegistrationRequestById(id);
        RegistrationRequestResponse registrationRequestResponse = registrationRequestMapper.registrationRequestToRegistrationRequestResponse(registrationRequest);
        logger.info("getRegistrationRequest - Registration request has been got");
        return registrationRequestResponse;
    }

    @Override
    public void acceptRegistrationRequest(Long id) {
        logger.info("acceptRegistrationRequest - Accepting registration request with id "+id);
        RegistrationRequest registrationRequest = getRegistrationRequestById(id);
        registrationRequest.setStatus(RequestStatus.ACCEPTED);
        registrationRequest.getUser().setStatus(UserStatus.ACTIVE);
        userRepository.save(registrationRequest.getUser());
        registrationRequestRepository.save(registrationRequest);
        logger.info("acceptRegistrationRequest - Registration request has been accepted");
    }

    @Override
    public void rejectRegistrationRequest(Long id) {
        logger.info("rejectRegistrationRequest - Rejecting registration request with id "+id);
        RegistrationRequest registrationRequest = getRegistrationRequestById(id);
        registrationRequest.setStatus(RequestStatus.REJECTED);
        registrationRequest.getUser().setStatus(UserStatus.DISABLED);
        userRepository.save(registrationRequest.getUser());
        registrationRequestRepository.save(registrationRequest);
        logger.info("rejectRegistrationRequest - Registration request has been rejected");
    }

    private RegistrationRequest getRegistrationRequestById(Long id){
        return registrationRequestRepository.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Registration request was not found by id "+id));
    }
}
