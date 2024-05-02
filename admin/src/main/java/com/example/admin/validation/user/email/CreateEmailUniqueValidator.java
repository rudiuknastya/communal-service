package com.example.admin.validation.user.email;

import com.example.admin.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateEmailUniqueValidator implements ConstraintValidator<CreateEmailUnique, String> {
    private final UserRepository userRepository;

    public CreateEmailUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByEmail(email);
    }
}
