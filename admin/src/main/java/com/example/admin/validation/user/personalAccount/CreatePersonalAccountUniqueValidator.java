package com.example.admin.validation.user.personalAccount;

import com.example.admin.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreatePersonalAccountUniqueValidator implements ConstraintValidator<CreatePersonalAccountUnique, String> {
    private final UserRepository userRepository;

    public CreatePersonalAccountUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String personalAccount, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByPersonalAccountAndDeletedIsFalse(personalAccount);
    }
}
