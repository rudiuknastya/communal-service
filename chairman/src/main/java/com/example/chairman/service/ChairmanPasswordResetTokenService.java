package com.example.chairman.service;


import com.example.chairman.model.authentication.EmailRequest;

public interface ChairmanPasswordResetTokenService {
    String createOrUpdatePasswordResetToken(EmailRequest emailRequest);
    boolean isPasswordResetTokenValid(String token);
    void updatePassword(String token, String password);
}
