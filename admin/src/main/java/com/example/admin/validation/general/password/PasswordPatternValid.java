package com.example.admin.validation.general.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = PasswordPatternValidValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordPatternValid {
    String message() default "Пароль має мати принаймні одну цифру, одну велику літеру, один спецсимвол ,./? та розмір від 8 до 100 символів";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
