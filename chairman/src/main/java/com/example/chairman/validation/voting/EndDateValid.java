package com.example.chairman.validation.voting;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;

@Constraint(validatedBy = EndDateValidValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface EndDateValid {
    String endDate();
    String startDate();
    String message() default "Невірна дата";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        EndDateValid[] value();
    }
}
