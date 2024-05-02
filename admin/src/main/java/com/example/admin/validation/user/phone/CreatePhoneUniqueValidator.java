package com.example.admin.validation.user.phone;

import com.example.admin.repository.UserRepository;
import com.example.admin.validation.user.phone.CreatePhoneUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreatePhoneUniqueValidator implements ConstraintValidator<CreatePhoneUnique, String> {
    private final UserRepository userRepository;

    public CreatePhoneUniqueValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }
}
