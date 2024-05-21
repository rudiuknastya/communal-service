package com.example.chairman.service;

import com.example.chairman.model.authentication.EmailRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface MailService {
    void sendToken(String token, EmailRequest emailRequest);
}
