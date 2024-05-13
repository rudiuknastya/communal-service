package com.example.admin.validation.user.file;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = XlsxFileExtensionValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface XlsxFileExtension {
    String message() default "Файл не відповідає формату .xlsx";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
