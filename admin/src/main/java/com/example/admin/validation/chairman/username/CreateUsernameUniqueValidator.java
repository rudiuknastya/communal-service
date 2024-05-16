package com.example.admin.validation.chairman.username;

import com.example.admin.repository.ChairmanRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateUsernameUniqueValidator implements ConstraintValidator<CreateUsernameUnique, String> {
    private final ChairmanRepository chairmanRepository;

    public CreateUsernameUniqueValidator(ChairmanRepository chairmanRepository) {
        this.chairmanRepository = chairmanRepository;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        return !chairmanRepository.existsByUsernameAndDeletedIsFalse(username);
    }
}
