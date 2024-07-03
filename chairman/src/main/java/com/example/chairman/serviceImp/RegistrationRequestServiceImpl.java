package com.example.chairman.serviceImp;

import com.example.chairman.entity.RegistrationRequest;
import com.example.chairman.mapper.RegistrationRequestMapper;
import com.example.chairman.model.registrationRequest.FilterRequest;
import com.example.chairman.model.registrationRequest.TableRegistrationRequestResponse;
import com.example.chairman.repository.RegistrationRequestRepository;
import com.example.chairman.service.RegistrationRequestService;
import com.example.chairman.specification.specificationFormer.RegistrationRequestSpecificationFormer;
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
    private final Logger logger = LogManager.getLogger(RegistrationRequestServiceImpl.class);

    public RegistrationRequestServiceImpl(RegistrationRequestRepository registrationRequestRepository, RegistrationRequestMapper registrationRequestMapper) {
        this.registrationRequestRepository = registrationRequestRepository;
        this.registrationRequestMapper = registrationRequestMapper;
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
}
