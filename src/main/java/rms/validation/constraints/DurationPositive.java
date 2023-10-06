package rms.validation.constraints;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * The annotated element must have a total duration length higher than zero
 * {@code null} elements are considered valid.
 */
@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = {})
public @interface DurationPositive {
    String message() default "Duration must be positive";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};
}
