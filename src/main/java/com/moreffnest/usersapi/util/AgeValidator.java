package com.moreffnest.usersapi.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<MinimumAge, LocalDate> {
    @Value("${min.age:18}")
    private int threshold;

    @Override
    public void initialize(MinimumAge constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        return localDate != null &&
                Period.between(localDate, LocalDate.now()).getYears() >= threshold;
    }
}
