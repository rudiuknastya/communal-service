package com.example.admin.validation.user.username;

import com.example.admin.validation.user.email.CreateEmailUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = CreateUsernameUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateUsernameUnique {
    String message() default "Логін вже використовується";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
