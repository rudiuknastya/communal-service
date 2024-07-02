package com.example.user.mapper;

import com.example.user.entity.Chairman;
import com.example.user.entity.RegistrationRequest;
import com.example.user.entity.User;
import com.example.user.entity.enums.RequestStatus;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RegistrationRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "chairman", source = "chairman")
    RegistrationRequest createRegistrationRequest(RequestStatus status, User user, Chairman chairman);
}
