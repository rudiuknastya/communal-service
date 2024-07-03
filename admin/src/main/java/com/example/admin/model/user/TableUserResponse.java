package com.example.admin.model.user;

import com.example.admin.entity.enums.UserStatus;

public record TableUserResponse(
        Long id,
        String fullName,
        String city,
        String street,
        String houseNumber,
        Long apartmentNumber,
        String personalAccount,
        String phoneNumber,
        UserStatus status
) {
}
