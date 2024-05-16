package com.example.admin.validation.chairman.username;

import com.example.admin.repository.ChairmanRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Map;

public class EditUsernameUniqueValidator implements ConstraintValidator<EditUsernameUnique, String> {
    private final ChairmanRepository chairmanRepository;
    private final HttpServletRequest httpServletRequest;

    public EditUsernameUniqueValidator(ChairmanRepository chairmanRepository, HttpServletRequest httpServletRequest) {
        this.chairmanRepository = chairmanRepository;
        this.httpServletRequest = httpServletRequest;
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        Object object =  httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Long> map = objectMapper
                .convertValue(object, new TypeReference<Map<String, Long>>() {});
        return !chairmanRepository.existsByUsernameAndDeletedIsFalseAndIdNot(username, map.get("id"));
    }
}
