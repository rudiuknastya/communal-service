package com.example.chairman.validation.general.image;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = ImageExtensionValidValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ImageExtensionValid {
    String message() default "Зображення не відповідає формату .jpeg, .jpg, .png";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
