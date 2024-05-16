package com.example.admin.validation.chairman.phoneNumber;

import com.example.admin.repository.ChairmanRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreatePhoneUniqueValidator implements ConstraintValidator<CreatePhoneUnique, String> {
    private final ChairmanRepository chairmanRepository;

    public CreatePhoneUniqueValidator(ChairmanRepository chairmanRepository) {
        this.chairmanRepository = chairmanRepository;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        return !chairmanRepository.existsByPhoneNumberAndDeletedIsFalse(phoneNumber);
    }
}
