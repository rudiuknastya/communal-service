package com.example.chairman.mapper;

import com.example.chairman.entity.House;
import com.example.chairman.entity.User;
import com.example.chairman.model.user.TableUserResponse;
import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "status", source = "status")
    @Mapping(target = "city", source = "house.city")
    @Mapping(target = "street", source = "house.street")
    @Mapping(target = "number", source = "house.number")
    UserResponse userToUserResponse(User user);
    @Mapping(target = "status", source = "userRequest.status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", source = "savedAvatar")
    @Mapping(target = "password", ignore = true)
    void updateUserWithoutPassword(@MappingTarget User user, UserRequest userRequest,
                                   String savedAvatar);
    @Mapping(target = "status", source = "userRequest.status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", source = "savedAvatar")
    @Mapping(target = "password", source = "encodedPassword")
    void updateUserWithPassword(@MappingTarget User user, UserRequest userRequest,
                                String savedAvatar, String encodedPassword);
    List<TableUserResponse> userListToTableUserResponseList(List<User> users);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getLastName()+\" \"+user.getFirstName()+\" \"+user.getMiddleName())")
    @Mapping(target = "city", source = "house.city")
    @Mapping(target = "street", source = "house.street")
    @Mapping(target = "houseNumber", source = "house.number")
    @Mapping(target = "status", source = "status")
    TableUserResponse userToTableUserResponse(User user);
}
