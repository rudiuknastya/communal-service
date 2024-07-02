package com.example.user.validation.registration.username;

import com.example.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameUniqueValidator implements ConstraintValidator<UsernameUnique, String> {
    private final UserRepository userRepository;

    public UsernameUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByUsernameAndDeletedIsFalse(username);
    }
}
