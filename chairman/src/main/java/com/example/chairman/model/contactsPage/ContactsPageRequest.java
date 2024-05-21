package com.example.chairman.model.contactsPage;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ContactsPageRequest(
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
        String firstTitle,
        @Size(max=8000, message = "Розмір поля має бути не більше 8000 символів")
        String firstText,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=1000, message = "Розмір поля має бути не більше 1000 символів")
        String firstTextWithoutTags,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
        String secondTitle,
        @Size(max=8000, message = "Розмір поля має бути не більше 8000 символів")
        String secondText,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=1000, message = "Розмір поля має бути не більше 1000 символів")
        String secondTextWithoutTags
) {
}
