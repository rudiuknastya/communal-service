package com.example.admin.model.chairmen;

import com.example.admin.entity.ChairmanStatus;

public record TableChairmanResponse(
        Long id,
        String fullName,
        String phoneNumber,
        ChairmanStatus status
) {
}
