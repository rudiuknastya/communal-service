package com.example.admin.validation.house;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
@Constraint(validatedBy = CreateHouseUniqueValidator.class)
@Target({TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateHouseUnique {
    String number();
    String street();
    String city();

    String message() default "Така адреса вже існує";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    @interface List {
        CreateHouseUnique[] value();
    }
}
