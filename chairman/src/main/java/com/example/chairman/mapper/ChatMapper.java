package com.example.chairman.mapper;

import com.example.chairman.entity.Chairman;
import com.example.chairman.entity.Chat;
import com.example.chairman.entity.User;
import com.example.chairman.model.chat.UserResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ChatMapper {
    List<UserResponse> userListToUserResponseList(List<User> users);
    @Mapping(target = "houseNumber", source = "house.number")
    @Mapping(target = "id", source = "id")
    @Mapping(target = "fullName", expression = "java(user.getLastName()+\" \"+user.getFirstName()+\" \"+user.getMiddleName())")
    UserResponse userToUserResponse(User user);
    @Mapping(target = "chairman", source = "chairman")
    @Mapping(target = "user", source = "user")
    @Mapping(target = "id", ignore = true)
    Chat createChat(Chairman chairman, User user);
}
