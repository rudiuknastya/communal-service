package com.example.admin.model.admin;

public record ProfileResponse(
        String firstName,
        String lastName,
        String middleName,
        String phoneNumber,
        String avatar,
        boolean faAuthentication
) {
}
