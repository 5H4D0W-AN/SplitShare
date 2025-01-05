package com.amitnehra.annotations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MandatoryFieldValidator implements ConstraintValidator<Mandatory, Object> {

    @Override
    public void initialize(Mandatory constraintAnnotation) {}
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (value instanceof String && ((String) value).trim().isEmpty()) {
            return false;
        }
        return true;
    }
}
