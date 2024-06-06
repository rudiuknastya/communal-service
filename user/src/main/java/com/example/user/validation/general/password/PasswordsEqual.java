package com.example.user.validation.general.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
@Constraint(validatedBy = PasswordsEqualValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordsEqual {
    String confirmPassword();
    String password();

    String message() default "Пароль має бути однаковим";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        PasswordsEqual[] value();
    }
}
