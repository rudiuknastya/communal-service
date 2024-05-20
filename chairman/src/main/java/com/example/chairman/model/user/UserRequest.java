package com.example.chairman.model.user;

import com.example.chairman.entity.UserStatus;
import com.example.chairman.validation.general.image.ImageExtensionValid;
import com.example.chairman.validation.general.password.PasswordPatternValid;
import com.example.chairman.validation.general.password.PasswordsEqual;
import com.example.chairman.validation.user.email.EditEmailUnique;
import com.example.chairman.validation.user.phone.EditPhoneUnique;
import com.example.chairman.validation.user.username.EditUsernameUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@PasswordsEqual(
        confirmPassword = "confirmPassword",
        password = "password"
)
public record UserRequest(
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
        @Pattern(regexp = "[a-zA-Z0-9._-]+@[a-zA-Z0-9-]+\\.[a-z]{2,3}(\\.[a-z]{2,3})?",
                message = "Пошта не відповідає формату")
        @EditEmailUnique
        String email,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(max = 13, message = "Розмір поля має бути не більше 13 символів")
        @Pattern(regexp = "\\+?380(50)?(66)?(95)?(99)?(67)?(68)?(96)?(97)?(98)?(63)?(93)?(73)?[0-9]{0,7}",
                message = "Номер не відповідає формату")
        @EditPhoneUnique
        String phoneNumber,
        @NotNull(message = "Поле не може бути порожнім")
        UserStatus status,
        @ImageExtensionValid
        MultipartFile avatar,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(min = 10, max=100, message = "Довжина поля від 10 до 100 символів")
        @EditUsernameUnique
        String username,
        @PasswordPatternValid
        String password,
        String confirmPassword
) {
}
