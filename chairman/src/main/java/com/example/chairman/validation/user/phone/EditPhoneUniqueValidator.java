package com.example.chairman.validation.user.phone;

import com.example.chairman.repository.UserRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class EditPhoneUniqueValidator implements ConstraintValidator<EditPhoneUnique, String> {
    private final UserRepository userRepository;
    private final HttpServletRequest httpServletRequest;

    public EditPhoneUniqueValidator(UserRepository userRepository, HttpServletRequest httpServletRequest) {
        this.userRepository = userRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext constraintValidatorContext) {
        Object object =  httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> map = objectMapper
                .convertValue(object, new TypeReference<Map<String, Long>>() {});
        return !userRepository.existsByPhoneNumberAndDeletedIsFalseAndIdNot(phoneNumber, map.get("id"));
    }
}
