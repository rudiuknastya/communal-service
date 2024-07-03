package com.example.admin.model.user;

import com.example.admin.entity.enums.UserStatus;
import com.example.admin.model.houses.HouseNumberResponse;

import java.math.BigDecimal;

public record UserResponse(
        String firstName,
        String lastName,
        String middleName,
        String email,
        String phoneNumber,
        Long apartmentNumber,
        String personalAccount,
        UserStatus status,
        BigDecimal area,
        String avatar,
        String username,
        String city,
        String street,
        HouseNumberResponse houseNumberResponse
) {
}
