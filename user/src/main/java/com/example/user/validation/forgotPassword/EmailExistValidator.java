package com.example.user.validation.forgotPassword;

import com.example.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExist,String> {
    private final UserRepository userRepository;

    public EmailExistValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return userRepository.existsByEmailAndDeletedIsFalse(email);
    }
}
