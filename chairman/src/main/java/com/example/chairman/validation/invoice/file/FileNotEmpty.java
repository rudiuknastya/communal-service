package com.example.chairman.validation.invoice.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = FileNotEmptyValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileNotEmpty {
    String message() default "Файл є обов'язковим";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
