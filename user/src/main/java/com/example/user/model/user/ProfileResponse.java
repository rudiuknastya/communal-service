package com.example.user.model.user;

import java.math.BigDecimal;

public record ProfileResponse(
        String firstName,
        String lastName,
        String middleName,
        String email,
        String phoneNumber,
        Long apartmentNumber,
        String personalAccount,
        BigDecimal area,
        String avatar,
        String address,
        String city
) {
}
