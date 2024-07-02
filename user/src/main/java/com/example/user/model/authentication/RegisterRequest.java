package com.example.user.model.authentication;

import com.example.user.validation.general.password.PasswordsEqual;
import com.example.user.validation.registration.email.EmailUnique;
import com.example.user.validation.registration.personalAccount.PersonalAccountUnique;
import com.example.user.validation.registration.phoneNumber.PhoneNumberUnique;
import com.example.user.validation.registration.username.UsernameUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
@PasswordsEqual(
        password = "password",
        confirmPassword = "confirmPassword"
)
public record RegisterRequest(
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=50, message = "Розмір поля має бути не більше 50 символів")
        String firstName,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=50, message = "Розмір поля має бути не більше 50 символів")
        String lastName,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=50, message = "Розмір поля має бути не більше 50 символів")
        String middleName,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max=100, message = "Розмір поля має бути не більше 100 символів")
        @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}(\\.[a-z]{2,3})?", message = "Пошта не відповідає формату")
        @EmailUnique
        String email,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max = 13, message = "Розмір поля має бути не більше 13 символів")
        @Pattern(regexp = "\\+?380(50)?(66)?(95)?(99)?(67)?(68)?(96)?(97)?(98)?(63)?(93)?(73)?[0-9]{7}",
                message = "Номер не відповідає формату")
        @PhoneNumberUnique
        String phoneNumber,
        @NotNull(message = "Поле не може бути порожнім")
        Long apartmentNumber,
        @NotBlank(message = "Поле не може бути порожнім")
        @Pattern(regexp="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}", message="Невірний формат рахунку")
        @PersonalAccountUnique
        String personalAccount,
        @NotNull(message = "Поле не може бути порожнім")
        BigDecimal area,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(min = 10, max=100, message = "Довжина поля від 10 до 100 символів")
        @UsernameUnique
        String username,
        @Pattern.List({
                @Pattern(regexp = ".*\\d+.*", message = "Пароль має мати принаймні одну цифру"),
                @Pattern(regexp = ".*[,./?]+.*", message = "Пароль має мати принаймні один спецсимвол ,./?"),
                @Pattern(regexp = ".*[A-Z]+.*", message = "Пароль має мати принаймні одну велику літеру")
        })
        @Size(min = 8, max = 100, message = "Довжина поля від 8 до 100 символів")
        @NotBlank(message = "Поле не може бути порожнім")
        String password,
        String confirmPassword,
        @NotNull(message = "Поле не може бути порожнім")
        Long houseId
) {
}
