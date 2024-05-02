package com.example.admin.validation.user.email;

import com.example.admin.validation.user.phone.CreatePhoneUniqueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = CreateEmailUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateEmailUnique {
    String message() default "Пошта вже використовується";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
