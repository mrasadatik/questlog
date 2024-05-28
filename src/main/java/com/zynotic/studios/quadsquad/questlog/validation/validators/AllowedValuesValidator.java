package com.zynotic.studios.quadsquad.questlog.validation.validators;

import com.zynotic.studios.quadsquad.questlog.annotations.AllowedValues;
import com.zynotic.studios.quadsquad.questlog.enums.DefaultEnum;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class AllowedValuesValidator implements ConstraintValidator<AllowedValues, Object> {

    private String[] valueList;
    private Class<? extends Enum<?>> enumClass;
    private Enum<?>[] enumConstants;

    @Override
    public void initialize(AllowedValues constraintAnnotation) {
        this.valueList = constraintAnnotation.valueList();
        this.enumClass = constraintAnnotation.enumClass();
        if (enumClass != null && enumClass != DefaultEnum.class && enumClass.isEnum()) {
            this.enumConstants = enumClass.getEnumConstants();
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Null values are considered valid
        }

        if (enumClass != null && enumClass != DefaultEnum.class && enumClass.isEnum()) {
            for (Enum<?> enumValue : enumConstants) {
                if (enumValue.name().equals(value.toString())) {
                    return true;
                }
            }
        }

        if (valueList.length > 0) {
            return Arrays.asList(valueList).contains(value.toString());
        }

        return false;
    }
}