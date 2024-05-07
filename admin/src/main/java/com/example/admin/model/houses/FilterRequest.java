package com.example.admin.model.houses;

import com.example.admin.entity.HouseStatus;

public record FilterRequest(
        int page,
        int pageSize,
        String city,
        String street,
        Long number,
        Long chairmanId,
        HouseStatus status
) {
}
