package com.example.user.mapper;

import com.example.user.entity.User;
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
}
