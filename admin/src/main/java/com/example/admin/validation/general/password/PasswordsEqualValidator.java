package com.example.admin.validation.general.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

public class PasswordsEqualValidator implements ConstraintValidator<PasswordsEqual, Object> {
    private String password;
    private String confirmPassword;
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        String passwordValue = (String) new BeanWrapperImpl(o).getPropertyValue(password);
        String confirmPasswordValue = (String) new BeanWrapperImpl(o).getPropertyValue(confirmPassword);
        return passwordValue.equals(confirmPasswordValue);
    }

    @Override
    public void initialize(PasswordsEqual passwordsEqual) {
        this.password = passwordsEqual.password();
        this.confirmPassword = passwordsEqual.confirmPassword();
    }
}
