package com.example.chairman.mapper;

import com.example.chairman.entity.RegistrationRequest;
import com.example.chairman.model.registrationRequest.TableRegistrationRequestResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RegistrationRequestMapper {
    List<TableRegistrationRequestResponse> registrationRequestListToTableRegistrationRequestResponseList(List<RegistrationRequest> registrationRequests);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "fullName", expression = "java(registrationRequest.getUser().getLastName()+\" \"+registrationRequest.getUser().getFirstName()+\" \"+registrationRequest.getUser().getMiddleName())")
    TableRegistrationRequestResponse registrationRequestToTableRegistrationRequestResponse (RegistrationRequest registrationRequest);
}
