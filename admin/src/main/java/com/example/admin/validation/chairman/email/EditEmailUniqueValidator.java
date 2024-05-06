package com.example.admin.validation.chairman.email;

import com.example.admin.repository.ChairmanRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class EditEmailUniqueValidator implements ConstraintValidator<EditEmailUnique, String> {
    private final HttpServletRequest httpServletRequest;
    private final ChairmanRepository chairmanRepository;

    public EditEmailUniqueValidator(HttpServletRequest httpServletRequest, ChairmanRepository chairmanRepository) {
        this.httpServletRequest = httpServletRequest;
        this.chairmanRepository = chairmanRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Object object =  httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> map = objectMapper
                .convertValue(object, new TypeReference<Map<String, Long>>() {});
        return !chairmanRepository.existsByEmailAndIdNot(email, map.get("id"));
    }
}
