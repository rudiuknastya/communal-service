package com.example.admin.validation.user.personalAccount;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
@Constraint(validatedBy = CreatePersonalAccountUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreatePersonalAccountUnique {
    String message() default "Особистий рахунок вже існує";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
