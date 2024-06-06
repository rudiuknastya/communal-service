package com.example.user.service;

import com.example.user.model.authentication.EmailRequest;

public interface PasswordResetTokenService {
    String createOrUpdatePasswordResetToken(EmailRequest emailRequest);
    boolean isPasswordResetTokenValid(String token);
    void updatePassword(String token, String password);
}
