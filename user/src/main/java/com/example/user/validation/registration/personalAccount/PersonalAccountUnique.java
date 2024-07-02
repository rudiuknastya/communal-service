package com.example.user.validation.registration.personalAccount;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

@Constraint(validatedBy = PersonalAccountUniqueValidator.class)
@Target({FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PersonalAccountUnique {
    String message() default "Такий особистий рахунок вже використовується";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
