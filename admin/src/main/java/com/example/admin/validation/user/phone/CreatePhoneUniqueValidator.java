package com.example.admin.validation.user.phone;

import com.example.admin.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreatePhoneUniqueValidator implements ConstraintValidator<CreatePhoneUnique, String> {
    private final UserRepository userRepository;

    public CreatePhoneUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByPhoneNumberAndDeletedIsFalse(phoneNumber);
    }
}
