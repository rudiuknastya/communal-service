package com.example.admin.model.chairmen;

import com.example.admin.entity.enums.ChairmanStatus;

public record FilterRequest(
        int page,
        int pageSize,
        String fullName,
        String phoneNumber,
        ChairmanStatus status
) {
}
