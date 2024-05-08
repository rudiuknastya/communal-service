package com.example.admin.mapper;

import com.example.admin.entity.House;
import com.example.admin.entity.User;
import com.example.admin.model.user.CreateUserRequest;
import com.example.admin.model.user.TableUserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    @Mapping(target = "status", source = "createUserRequest.status")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "avatar", source = "savedAvatar")
    @Mapping(target = "house", source = "house")
    User createUser(CreateUserRequest createUserRequest, String savedAvatar, House house);
    List<TableUserResponse> userListToTableUserResponseList(List<User> users);
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getLastName()+\" \"+user.getFirstName()+\" \"+user.getMiddleName())")
    @Mapping(target = "city", source = "house.city")
    @Mapping(target = "street", source = "house.street")
    @Mapping(target = "houseNumber", source = "house.number")
    @Mapping(target = "status", source = "status")
    TableUserResponse userToTableUserResponse(User user);
}
