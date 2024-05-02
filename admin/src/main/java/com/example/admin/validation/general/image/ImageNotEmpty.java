package com.example.admin.validation.general.image;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = ImageNotEmptyValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageNotEmpty {
    String message() default "Зображення є обов'язковим";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
