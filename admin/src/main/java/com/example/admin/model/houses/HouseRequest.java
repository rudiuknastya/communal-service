package com.example.admin.model.houses;

import com.example.admin.entity.HouseStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record HouseRequest(
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
        String city,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=200, message = "Розмір поля має бути не більше 200 символів")
        String street,
        @NotNull(message = "Поле не може бути порожнім")
        Long number,
        @NotNull(message = "Поле не може бути порожнім")
        HouseStatus status,
        @NotNull(message = "Поле не може бути порожнім")
        Long chairmanId
) {
}
