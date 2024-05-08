package com.example.admin.model.user;

import com.example.admin.entity.UserStatus;

public record FilterRequest(
        int page,
        int pageSize,
        String fullName,
        String city,
        String street,
        Long houseNumber,
        Long apartmentNumber,
        String personalAccount,
        String phoneNumber,
        UserStatus status
) {
}
