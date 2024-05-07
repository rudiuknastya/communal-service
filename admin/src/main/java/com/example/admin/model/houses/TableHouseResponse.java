package com.example.admin.model.houses;

import com.example.admin.entity.HouseStatus;

public record TableHouseResponse(
        Long id,
        String city,
        String street,
        Long number,
        String chairman,
        HouseStatus status
) {
}
