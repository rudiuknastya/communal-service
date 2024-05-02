package com.example.admin.mapper;

import com.example.admin.entity.House;
import com.example.admin.entity.User;
import com.example.admin.model.user.CreateUserRequest;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
//    User createUser(CreateUserRequest createUserRequest, String avatar, House house);
}
