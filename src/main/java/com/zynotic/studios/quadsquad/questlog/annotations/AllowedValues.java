package com.zynotic.studios.quadsquad.questlog.annotations;

import com.zynotic.studios.quadsquad.questlog.enums.DefaultEnum;
import com.zynotic.studios.quadsquad.questlog.validation.validators.AllowedValuesValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AllowedValuesValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface AllowedValues {
    String message() default "Invalid value.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String[] valueList() default {};

    Class<? extends Enum<?>> enumClass() default DefaultEnum.class;
}
