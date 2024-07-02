package com.example.user.validation.registration.personalAccount;

import com.example.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PersonalAccountUniqueValidator implements ConstraintValidator<PersonalAccountUnique, String> {
    private final UserRepository userRepository;

    public PersonalAccountUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String personalAccount, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByPersonalAccountAndDeletedIsFalse(personalAccount);
    }
}
