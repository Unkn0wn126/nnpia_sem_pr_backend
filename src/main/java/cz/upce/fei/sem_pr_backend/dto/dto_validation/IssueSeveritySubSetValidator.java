package cz.upce.fei.sem_pr_backend.dto.dto_validation;

import cz.upce.fei.sem_pr_backend.domain.enum_type.IssueSeverity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class IssueSeveritySubSetValidator implements ConstraintValidator<IssueSeveritySubset, IssueSeverity> {
    private IssueSeverity[] subset;

    @Override
    public void initialize(IssueSeveritySubset constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(IssueSeverity value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
