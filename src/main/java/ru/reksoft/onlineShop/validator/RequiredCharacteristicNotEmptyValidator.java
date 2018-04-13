package ru.reksoft.onlineShop.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredCharacteristicNotEmptyValidator
        implements ConstraintValidator<RequiredCharacteristicNotEmpty, Object> {

    private String characteristicValue;
    private String required;

    public void initialize(RequiredCharacteristicNotEmpty constraintAnnotation) {
        this.characteristicValue = constraintAnnotation.characteristicValue();
        this.required = constraintAnnotation.required();
    }

    public boolean isValid(Object value,
                           ConstraintValidatorContext context) {
        String characteristicValue = (String) new BeanWrapperImpl(value)
                .getPropertyValue(this.characteristicValue);
        boolean fieldMatchValue = (boolean) new BeanWrapperImpl(value)
                .getPropertyValue(required);
        if (characteristicValue.equals("") && fieldMatchValue) {
            return false;
        } else {
            return true;
        }
    }
}