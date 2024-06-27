package com.example.chairman.model.user;


import com.example.chairman.entity.enums.UserStatus;

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
