package com.example.admin.validation.house;

import com.example.admin.model.houses.HouseRequest;
import com.example.admin.repository.HouseRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CreateHouseUniqueValidator implements ConstraintValidator<CreateHouseUnique, HouseRequest> {
    private final HouseRepository houseRepository;

    public CreateHouseUniqueValidator(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @Override
    public boolean isValid(HouseRequest houseRequest, ConstraintValidatorContext constraintValidatorContext) {
        return !houseRepository
                .existsByCityAndStreetAndNumberAndDeletedIsFalse(houseRequest.city(),
                        houseRequest.street(), houseRequest.number());
    }
}
