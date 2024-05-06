package com.example.admin.validation.chairman.email;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = EditEmailUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EditEmailUnique {
    String message() default "Пошта вже використовується";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
