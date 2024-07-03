package com.example.admin.model.chairmen;

import com.example.admin.entity.enums.ChairmanStatus;

public record ChairmanResponse(
        String firstName,
        String lastName,
        String middleName,
        ChairmanStatus status,
        String email,
        String phoneNumber,
        String username,
        String avatar
) {
}
