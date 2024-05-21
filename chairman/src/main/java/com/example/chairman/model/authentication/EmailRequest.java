package com.example.chairman.model.authentication;

import com.example.chairman.validation.forgotPassword.EmailExist;
import jakarta.validation.constraints.NotBlank;
public record EmailRequest(
        @EmailExist
        @NotBlank(message = "Обов'язкове поле")
        String email) {
}
