package com.example.admin.validation.chairman.phoneNumber;

import com.example.admin.repository.ChairmanRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class EditPhoneNumberUniqueValidator implements ConstraintValidator<EditPhoneNumberUnique, String> {
    private final ChairmanRepository chairmanRepository;
    private final HttpServletRequest httpServletRequest;

    public EditPhoneNumberUniqueValidator(ChairmanRepository chairmanRepository, HttpServletRequest httpServletRequest) {
        this.chairmanRepository = chairmanRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        Object object =  httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> map = objectMapper
                .convertValue(object, new TypeReference<Map<String, Long>>() {});
        return !chairmanRepository.existsByPhoneNumberAndIdNot(phoneNumber, map.get("id"));
    }
}
