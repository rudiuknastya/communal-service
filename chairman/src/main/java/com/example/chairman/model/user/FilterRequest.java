package com.example.chairman.model.user;

import com.example.chairman.entity.enums.UserStatus;

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
