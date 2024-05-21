package com.example.chairman.validation.forgotPassword;

import com.example.chairman.repository.ChairmanRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmailExistValidator implements ConstraintValidator<EmailExist,String> {
    private final ChairmanRepository chairmanRepository;

    public EmailExistValidator(ChairmanRepository chairmanRepository) {
        this.chairmanRepository = chairmanRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return chairmanRepository.existsByEmailAndDeletedIsFalse(email);
    }
}
