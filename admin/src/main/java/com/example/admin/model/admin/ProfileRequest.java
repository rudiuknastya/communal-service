package com.example.admin.model.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record ProfileRequest(
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max = 50, message = "Розмір поля може бути не більше 50 символів")
        String firstName,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max = 50, message = "Розмір поля може бути не більше 50 символів")
        String lastName,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max = 50, message = "Розмір поля може бути не більше 50 символів")
        String middleName,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max = 13, message = "Розмір поля може бути не більше 13 символів")
        @Pattern(regexp = "\\+?380(50)?(66)?(95)?(99)?(67)?(68)?(96)?(97)?(98)?(63)?(93)?(73)?[0-9]{0,7}", message = "Телефон не відповідає формату")
        String phoneNumber,
        MultipartFile avatar,
        boolean faAuthentication
) {

}
