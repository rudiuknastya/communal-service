package com.example.chairman.model.user;


import com.example.chairman.entity.UserStatus;

public record UserResponse(
        String firstName,
        String lastName,
        String middleName,
        String email,
        String phoneNumber,
        UserStatus status,
        String avatar,
        String username
) {
}
