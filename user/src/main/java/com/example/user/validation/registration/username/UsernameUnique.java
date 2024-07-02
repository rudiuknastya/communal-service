package com.example.user.validation.registration.username;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = UsernameUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UsernameUnique {
    String message() default "Такий логін вже використовується";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
