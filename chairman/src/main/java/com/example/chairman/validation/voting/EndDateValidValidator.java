package com.example.chairman.validation.voting;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDateTime;

public class EndDateValidValidator implements ConstraintValidator<EndDateValid, Object> {
    private String endDate;
    private String startDate;
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        LocalDateTime endDateValue = (LocalDateTime) new BeanWrapperImpl(o).getPropertyValue(endDate);
        LocalDateTime startDateValue = (LocalDateTime) new BeanWrapperImpl(o).getPropertyValue(startDate);
        if(endDateValue != null && startDateValue != null) {
            return endDateValue.isAfter(LocalDateTime.now()) && endDateValue.isAfter(startDateValue);
        }
        return true;
    }

    @Override
    public void initialize(EndDateValid constraintAnnotation) {
        this.endDate = constraintAnnotation.endDate();
        this.startDate = constraintAnnotation.startDate();
    }
}
