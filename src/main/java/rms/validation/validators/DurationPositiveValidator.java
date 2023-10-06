package rms.validation.validators;

import java.time.Duration;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import rms.validation.constraints.DurationPositive;

@ApplicationScoped
public class DurationPositiveValidator implements ConstraintValidator<DurationPositive, Duration> {

    @Override
    public boolean isValid(Duration value, ConstraintValidatorContext context) {
        return !value.isNegative();
    }

}
