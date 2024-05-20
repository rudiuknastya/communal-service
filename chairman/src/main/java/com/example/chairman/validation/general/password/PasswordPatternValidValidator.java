package com.example.chairman.validation.general.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordPatternValidValidator implements ConstraintValidator<PasswordPatternValid, String> {
    @Override
    public boolean isValid(String password, ConstraintValidatorContext constraintValidatorContext) {
        if(password.isEmpty()){
            return true;
        }
        Pattern size = Pattern.compile(".{8,100}");
        Pattern atLeastOneDigit = Pattern.compile(".*\\d+.*");
        Pattern atLeastOneSymbol = Pattern.compile(".*[,./?]+.*");
        Pattern atLeastOneUpperCase = Pattern.compile(".*[A-Z]+.*");
        Matcher matcherSize = size.matcher(password);
        Matcher matcherDigit = atLeastOneDigit.matcher(password);
        Matcher matcherSymbol = atLeastOneSymbol.matcher(password);
        Matcher matcherUpperCase = atLeastOneUpperCase.matcher(password);
        return matcherSize.matches() && matcherDigit.matches()
                && matcherSymbol.matches() && matcherUpperCase.matches();
    }
}
