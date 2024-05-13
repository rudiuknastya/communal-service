package com.example.admin.validation.house;

import com.example.admin.model.houses.HouseRequest;
import com.example.admin.repository.HouseRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class EditHouseUniqueValidator implements ConstraintValidator<EditHouseUnique, HouseRequest> {
    private final HouseRepository houseRepository;
    private final HttpServletRequest httpServletRequest;

    public EditHouseUniqueValidator(HouseRepository houseRepository, HttpServletRequest httpServletRequest) {
        this.houseRepository = houseRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean isValid(HouseRequest houseRequest, ConstraintValidatorContext constraintValidatorContext) {
        Object object =  httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> map = objectMapper
                .convertValue(object, new TypeReference<Map<String, Long>>() {});
        return !houseRepository
                .existsByCityAndStreetAndNumberAndDeletedIsFalseAndIdNot(houseRequest.city(),
                        houseRequest.street(), houseRequest.number(), map.get("id"));
    }
}
