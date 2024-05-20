package com.example.chairman.service;

import com.example.chairman.model.user.UserRequest;
import com.example.chairman.model.user.UserResponse;

public interface UserService {
    void updateUser(UserRequest userRequest, Long id);
    UserResponse getUserResponse(Long id);
}
