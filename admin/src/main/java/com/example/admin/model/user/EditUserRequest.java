package com.example.admin.model.user;

import com.example.admin.entity.UserStatus;
import com.example.admin.validation.general.groups.Create;
import com.example.admin.validation.general.groups.Edit;
import com.example.admin.validation.general.image.ImageExtensionValid;
import com.example.admin.validation.general.password.PasswordPatternValid;
import com.example.admin.validation.general.password.PasswordsEqual;
import com.example.admin.validation.user.email.CreateEmailUnique;
import com.example.admin.validation.user.email.EditEmailUnique;
import com.example.admin.validation.user.personalAccount.CreatePersonalAccountUnique;
import com.example.admin.validation.user.personalAccount.EditPersonalAccountUnique;
import com.example.admin.validation.user.phone.CreatePhoneUnique;
import com.example.admin.validation.user.phone.EditPhoneUnique;
import com.example.admin.validation.user.username.CreateUsernameUnique;
import com.example.admin.validation.user.username.EditUsernameUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@PasswordsEqual(
        confirmPassword = "confirmPassword",
        password = "password"
)
public record EditUserRequest(
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
        Long apartmentNumber,
        @NotBlank(message = "Поле не може бути порожнім")
        @Pattern(regexp="[0-9]{4}-[0-9]{4}-[0-9]{4}-[0-9]{4}",
                message="Невірний формат рахунку")
        @EditPersonalAccountUnique
        String personalAccount,
        @NotNull(message = "Поле не може бути порожнім")
        UserStatus status,
        @NotNull(message = "Поле не може бути порожнім")
        BigDecimal area,
        @ImageExtensionValid
        MultipartFile avatar,
        @NotBlank(message = "Поле не може бути порожнім")
        @Size(min = 10, max=100, message = "Довжина поля від 10 до 100 символів")
        @EditUsernameUnique
        String username,
        @PasswordPatternValid
        String password,
        String confirmPassword,
        @NotNull(message = "Поле не може бути порожнім")
        Long number
) {
}
