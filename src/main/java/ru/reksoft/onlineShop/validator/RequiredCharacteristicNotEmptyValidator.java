package ru.reksoft.onlineShop.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredCharacteristicNotEmptyValidator
        implements ConstraintValidator<RequiredCharacteristicNotEmpty, Interface1> {

    public boolean isValid(Interface1 characteristic, ConstraintValidatorContext context) {
        return !characteristic.getValue().isEmpty() || !characteristic.isRequired();
    }
}