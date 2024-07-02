package com.example.user.mapper;

import com.example.user.entity.House;
import com.example.user.entity.User;
import com.example.user.entity.enums.UserStatus;
import com.example.user.model.authentication.RegisterRequest;
import com.example.user.model.user.ProfileResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "city", source = "house.city")
    @Mapping(target = "address",
            expression = "java(\"вул \"+user.getHouse().getStreet()+\" \"+user.getHouse().getNumber())")
    ProfileResponse userToProfileResponse(User user);
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "house", source = "house")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "avatar", source = "avatar")
    User registerRequestToUser(RegisterRequest registerRequest, House house,
                               UserStatus status, String avatar);
}
