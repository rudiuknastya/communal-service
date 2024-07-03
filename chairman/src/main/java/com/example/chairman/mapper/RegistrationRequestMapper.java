package com.example.chairman.mapper;

import com.example.chairman.entity.RegistrationRequest;
import com.example.chairman.model.registrationRequest.RegistrationRequestResponse;
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
    @Mapping(target = "fullName", expression = "java(registrationRequest.getUser().getLastName()+\" \"+registrationRequest.getUser().getFirstName()+\" \"+registrationRequest.getUser().getMiddleName())")
    @Mapping(target = "apartmentNumber", source = "user.apartmentNumber")
    @Mapping(target = "area", source = "user.area")
    @Mapping(target = "personalAccount", source = "user.personalAccount")
    @Mapping(target = "phoneNumber", source = "user.phoneNumber")
    @Mapping(target = "email", source = "user.email")
    @Mapping(target = "houseResponse.city", source = "user.house.city")
    @Mapping(target = "houseResponse.street", source = "user.house.street")
    @Mapping(target = "houseResponse.number", source = "user.house.number")
    RegistrationRequestResponse registrationRequestToRegistrationRequestResponse(RegistrationRequest registrationRequest);
}
