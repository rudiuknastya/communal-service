package com.example.admin.mapper;

import com.example.admin.entity.House;
import com.example.admin.entity.User;
import com.example.admin.model.user.CreateUserRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "status", source = "createUserRequest.status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", source = "savedAvatar")
    @Mapping(target = "house", source = "house")
    User createUser(CreateUserRequest createUserRequest, String savedAvatar, House house);
}
