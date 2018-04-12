package ru.reksoft.onlineShop.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CharacteristicValidator implements ConstraintValidator<ValidCharacteristic, String> {

    private boolean required;

    @Override
    public void initialize(ValidCharacteristic constraintAnnotation) {
        required=constraintAnnotation.required();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
       return  (value == null && required) ?  false: true;
    }
}
