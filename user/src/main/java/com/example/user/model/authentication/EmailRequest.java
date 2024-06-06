package com.example.user.model.authentication;

import com.example.user.validation.forgotPassword.EmailExist;
import jakarta.validation.constraints.NotBlank;
public record EmailRequest(
        @EmailExist
        @NotBlank(message = "Обов'язкове поле")
        String email) {
}
