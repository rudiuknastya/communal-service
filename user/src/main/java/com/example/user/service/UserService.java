package com.example.user.service;

import com.example.user.model.authentication.RegisterRequest;

public interface UserService {
    void register(RegisterRequest registerRequest);
}
