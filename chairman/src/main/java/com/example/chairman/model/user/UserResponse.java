package com.example.chairman.model.user;


import com.example.chairman.entity.UserStatus;

import java.math.BigDecimal;

public record UserResponse(
        String firstName,
        String lastName,
        String middleName,
        String email,
        String phoneNumber,
        UserStatus status,
        String avatar,
        String username,
        BigDecimal area,
        Long apartmentNumber,
        String personalAccount,
        String city,
        String street,
        String number
) {
}
