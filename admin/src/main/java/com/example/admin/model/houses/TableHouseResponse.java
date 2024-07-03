package com.example.admin.model.houses;

import com.example.admin.entity.enums.HouseStatus;

public record TableHouseResponse(
        Long id,
        String city,
        String street,
        String number,
        String chairman,
        HouseStatus status
) {
}
