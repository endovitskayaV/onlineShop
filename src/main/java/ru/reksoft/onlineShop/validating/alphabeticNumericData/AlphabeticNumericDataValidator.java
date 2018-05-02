package ru.reksoft.onlineShop.validating.alphabeticNumericData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class AlphabeticNumericDataValidator implements ConstraintValidator<AlphabeticNumericData, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        //only alphabetic symbols, numbers -w and spaces- s allowed
        return value == null || value.matches("[\\w\\s]*");
    }
}
