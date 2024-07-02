package com.example.user.validation.registration.phoneNumber;

import com.example.user.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneNumberUniqueValidator implements ConstraintValidator<PhoneNumberUnique, String> {
    private final UserRepository userRepository;

    public PhoneNumberUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByPhoneNumberAndDeletedIsFalse(phoneNumber);
    }
}
