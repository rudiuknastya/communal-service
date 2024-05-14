package com.example.admin.validation.user.phone;

import com.example.admin.validation.user.email.EditEmailUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = EditPhoneUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface EditPhoneUnique {
    String message() default "Номер телефону вже використовується";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
