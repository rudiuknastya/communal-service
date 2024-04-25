package com.example.admin.mapper;

import com.example.admin.entity.Admin;
import com.example.admin.model.admin.ProfileRequest;
import com.example.admin.model.admin.ProfileResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AdminMapper {
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "middleName", source = "middleName")
    @Mapping(target = "password", source = "encodedPassword")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "avatar", source = "avatar")
    Admin createFirstAdmin(String firstName, String lastName, String middleName,
                           String encodedPassword, String email, String phoneNumber, String avatar);

    ProfileResponse adminToProfileResponse(Admin admin);
    @Mapping(target = "avatar", source = "avatar")
    void setAdmin(@MappingTarget Admin admin, ProfileRequest profileRequest, String avatar);
}
