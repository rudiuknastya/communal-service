package com.example.chairman.service;

import com.example.chairman.model.registrationRequest.FilterRequest;
import com.example.chairman.model.registrationRequest.TableRegistrationRequestResponse;
import org.springframework.data.domain.Page;

public interface RegistrationRequestService {
    Page<TableRegistrationRequestResponse> getRegistrationRequestsForTable(FilterRequest filterRequest);
    void deleteRequestsByIds(Long [] ids);
}
