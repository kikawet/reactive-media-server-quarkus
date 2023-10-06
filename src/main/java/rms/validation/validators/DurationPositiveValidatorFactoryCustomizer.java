package rms.validation.validators;

import org.hibernate.validator.BaseHibernateValidatorConfiguration;
import org.hibernate.validator.cfg.ConstraintMapping;

import io.quarkus.hibernate.validator.ValidatorFactoryCustomizer;
import jakarta.enterprise.context.ApplicationScoped;
import rms.validation.constraints.DurationPositive;

@ApplicationScoped
public class DurationPositiveValidatorFactoryCustomizer implements ValidatorFactoryCustomizer {

    @Override
    public void customize(BaseHibernateValidatorConfiguration<?> configuration) {
        ConstraintMapping constraintMapping = configuration.createConstraintMapping();

        constraintMapping
                .constraintDefinition(DurationPositive.class)
                .includeExistingValidators(true)
                .validatedBy(DurationPositiveValidator.class);

        configuration.addMapping(constraintMapping);
    }
}
