package com.zynotic.studios.quadsquad.questlog.validation.validators;

import com.zynotic.studios.quadsquad.questlog.annotations.YearsElapsed;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

import static com.zynotic.studios.quadsquad.questlog.configs.AppConfig.getRequiredApplicationProperty;

public class YearsElapsedValidator implements ConstraintValidator<YearsElapsed, LocalDate> {

    private static final String APP_DEFAULT_TIMEZONE = getRequiredApplicationProperty("APP_DEFAULT_TIMEZONE");

    private int minYears;
    private int maxYears;

    @Override
    public void initialize(YearsElapsed constraintAnnotation) {
        this.minYears = constraintAnnotation.min();
        this.maxYears = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        LocalDate now = LocalDate.now(ZoneId.of(APP_DEFAULT_TIMEZONE));
        int yearsElapsed = Period.between(value, now).getYears();

        return yearsElapsed >= minYears && yearsElapsed <= maxYears;
    }
}