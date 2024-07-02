package com.example.user.service;

import com.example.user.entity.Chairman;
import com.example.user.entity.User;

public interface RegistrationRequestService {
    void createRegistrationRequest(User user, Chairman chairman);
}
