package com.example.chairman.service;

import com.example.chairman.model.authentication.EmailRequest;

public interface MailService {
    void sendChairmanPasswordResetToken(String token, EmailRequest emailRequest);
}
