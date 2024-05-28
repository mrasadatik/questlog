package com.zynotic.studios.quadsquad.questlog.annotations;

import com.zynotic.studios.quadsquad.questlog.validation.validators.YearsElapsedValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.ReportAsSingleViolation;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = YearsElapsedValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
public @interface YearsElapsed {
    String message() default "Invalid years elapsed. Should be at least {min} years.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int min() default 0;

    int max() default Integer.MAX_VALUE; // Making max optional
}
