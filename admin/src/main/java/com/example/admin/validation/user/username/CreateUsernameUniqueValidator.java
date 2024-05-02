package com.example.admin.validation.user.username;

import com.example.admin.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateUsernameUniqueValidator implements ConstraintValidator<CreateUsernameUnique, String> {
    private final UserRepository userRepository;

    public CreateUsernameUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByUsername(username);
    }
}
