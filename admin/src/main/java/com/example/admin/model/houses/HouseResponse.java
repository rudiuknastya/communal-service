package com.example.admin.model.houses;

import com.example.admin.entity.HouseStatus;

public record HouseResponse(
        String city,
        String street,
        String number,
        HouseStatus status,
        ChairmanNameResponse chairmanNameResponse
) {
}
