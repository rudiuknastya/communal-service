package com.example.user.validation.registration;

import com.example.user.model.authentication.RegisterRequest;
import com.example.user.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Set;

@Component
public class RegistrationValidation implements Validator {
    private final UserRepository userRepository;

    public RegistrationValidation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest registerRequest = (RegisterRequest) target;

        jakarta.validation.Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<RegisterRequest>> violations = validator.validate(registerRequest);
        for(ConstraintViolation<RegisterRequest> violation: violations){
            errors.rejectValue(violation.getPropertyPath().toString(), violation.getMessage(), violation.getMessage());
        }
        checkIfEmailIsUnique(registerRequest.email(), errors);
        checkIfPhoneNumberUnique(registerRequest.phoneNumber(), errors);
        checkIfUserNameIsUnique(registerRequest.username(), errors);
        checkIfPersonalAccountIsUnique(registerRequest.personalAccount(), errors);
        passwordAndConfirmPasswordMatch(registerRequest.password(), registerRequest.confirmPassword(), errors);
    }

    private void passwordAndConfirmPasswordMatch(String password, String confirmPassword, Errors errors) {
        if(password != null && password.equals(confirmPassword)){
            errors.rejectValue("confirmPassword", "confirmPassword", "Пароль має бути однаковим");
        }
    }

    private void checkIfPersonalAccountIsUnique(String personalAccount, Errors errors) {
        if(userRepository.existsByPersonalAccountAndDeletedIsFalse(personalAccount)){
            System.out.println(personalAccount);
            errors.rejectValue("personalAccount", "personalAccount", "Такий особистий рахунок вже існує");
        }
    }

    private void checkIfUserNameIsUnique(String username, Errors errors) {
        if(userRepository.existsByUsernameAndDeletedIsFalse(username)){
            errors.rejectValue("username", "username", "Такий логін вже існує");
        }
    }

    private void checkIfPhoneNumberUnique(String phoneNumber, Errors errors) {
        if(userRepository.existsByPhoneNumberAndDeletedIsFalse(phoneNumber)){
            errors.rejectValue("phoneNumber", "phoneNumber", "Такий номер телефону вже існує");
        }
    }

    private void checkIfEmailIsUnique(String email, Errors errors){
        if(userRepository.existsByEmailAndDeletedIsFalse(email)){
            errors.rejectValue("email", "email", "Така пошта вже існує");
        }
    }
}
