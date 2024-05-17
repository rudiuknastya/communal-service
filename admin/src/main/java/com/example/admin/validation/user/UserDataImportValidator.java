package com.example.admin.validation.user;

import com.example.admin.model.user.HouseDataImportDto;
import com.example.admin.model.user.UserDataImportRequest;
import com.example.admin.repository.HouseRepository;
import com.example.admin.repository.UserRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.hibernate.validator.internal.engine.ConstraintViolationImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.stereotype.Component;

import java.util.Set;
@Component
public class UserDataImportValidator {
    private final UserRepository userRepository;
    private final HouseRepository houseRepository;

    public UserDataImportValidator(UserRepository userRepository, HouseRepository houseRepository) {
        this.userRepository = userRepository;
        this.houseRepository = houseRepository;
    }

    public void validateForUniqueness(UserDataImportRequest importRequest,
                                      Set<ConstraintViolation<UserDataImportRequest>> violations){
        checkPhoneNumber(importRequest.getPhoneNumber(), violations);
        checkEmail(importRequest.getEmail(), violations);
        checkPersonalAccount(importRequest.getPersonalAccount(), violations);
        checkUsername(importRequest.getUsername(), violations);
    }

    private void checkPhoneNumber(String phoneNumber, Set<ConstraintViolation<UserDataImportRequest>> violations) {
        if(userRepository.existsByPhoneNumberAndDeletedIsFalse(phoneNumber)){
            Path path = PathImpl.createPathFromString("phoneNumber");
            ConstraintViolation<UserDataImportRequest> violation =  ConstraintViolationImpl.forBeanValidation("Номер телефону вже використовується", null, null, "Номер телефону вже використовується", UserDataImportRequest.class, new UserDataImportRequest(), null, null, path, null, null);
            violations.add(violation);
        }
    }

    private void checkEmail(String email, Set<ConstraintViolation<UserDataImportRequest>> violations) {
        if(userRepository.existsByEmailAndDeletedIsFalse(email)){
            Path path = PathImpl.createPathFromString("email");
            ConstraintViolation<UserDataImportRequest> violation =  ConstraintViolationImpl.forBeanValidation("Пошта вже використовується", null, null, "Пошта вже використовується", UserDataImportRequest.class, new UserDataImportRequest(), null, null, path, null, null);
            violations.add(violation);
        }
    }

    private void checkPersonalAccount(String personalAccount, Set<ConstraintViolation<UserDataImportRequest>> violations) {
        if (userRepository.existsByPersonalAccountAndDeletedIsFalse(personalAccount)){
            Path path = PathImpl.createPathFromString("personalAccount");
            ConstraintViolation<UserDataImportRequest> violation =  ConstraintViolationImpl.forBeanValidation("Особистий рахунок вже існує", null, null, "Особистий рахунок вже існує", UserDataImportRequest.class, new UserDataImportRequest(), null, null, path, null, null);
            violations.add(violation);
        }
    }

    private void checkUsername(String username, Set<ConstraintViolation<UserDataImportRequest>> violations) {
        if(userRepository.existsByUsernameAndDeletedIsFalse(username)){
            Path path = PathImpl.createPathFromString("username");
            ConstraintViolation<UserDataImportRequest> violation =  ConstraintViolationImpl.forBeanValidation("Логін вже використовується", null, null, "Логін вже використовується", UserDataImportRequest.class, new UserDataImportRequest(), null, null, path, null, null);
            violations.add(violation);
        }
    }
    public void validateHouseExist(HouseDataImportDto houseDataImportDto,
                                   Set<ConstraintViolation<UserDataImportRequest>> violations){
        if(!houseRepository.existsByCityAndStreetAndNumberAndDeletedIsFalse(houseDataImportDto.getCity(), houseDataImportDto.getStreet(), houseDataImportDto.getNumber())){
            Path path = PathImpl.createPathFromString("house");
            ConstraintViolation<UserDataImportRequest> violation =  ConstraintViolationImpl.forBeanValidation("Такого будинку не існує", null, null, "Такого будинку не існує", UserDataImportRequest.class, new UserDataImportRequest(), null, null, path, null, null);
            violations.add(violation);
        }
    }
}
