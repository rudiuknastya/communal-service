package com.example.admin.validation.chairman.email;

import com.example.admin.repository.ChairmanRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateEmailUniqueValidator implements ConstraintValidator<CreateEmailUnique, String> {
    private final ChairmanRepository chairmanRepository;

    public CreateEmailUniqueValidator(ChairmanRepository chairmanRepository) {
        this.chairmanRepository = chairmanRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return !chairmanRepository.existsByEmailAndDeleted(email, false);
    }
}
