package ru.reksoft.onlineShop.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredCharacteristicNotEmptyValidator
        implements ConstraintValidator<RequiredCharacteristicNotEmpty, CharacteristicPresentor> {

    public boolean isValid(CharacteristicPresentor characteristic, ConstraintValidatorContext context) {
        return !characteristic.getValue().isEmpty() || !characteristic.isRequired();
    }
}