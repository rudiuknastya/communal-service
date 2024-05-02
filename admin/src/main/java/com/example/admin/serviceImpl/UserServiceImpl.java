package com.example.admin.serviceImpl;

import com.example.admin.entity.House;
import com.example.admin.entity.User;
import com.example.admin.mapper.UserMapper;
import com.example.admin.model.user.CreateUserRequest;
import com.example.admin.repository.UserRepository;
import com.example.admin.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void createUser(CreateUserRequest createUserRequest) {
//        User user = userMapper.createUser(createUserRequest, "", new House());
    }
}
