package com.example.user.model.messages;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record MessageRequest(
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
        String subject,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=500, message = "Розмір поля має бути не більше 500 символів")
        String text,
        @NotNull(message = "Поле не може бути порожнім")
        Long chairmanId
) {
}
