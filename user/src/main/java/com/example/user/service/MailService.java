package com.example.user.service;

import com.example.user.model.authentication.EmailRequest;

public interface MailService {
    void sendToken(String token, EmailRequest emailRequest);
}
