package ru.reksoft.onlineShop.validating.characteristicRequiredValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredCharacteristicNotEmptyValidator
        implements ConstraintValidator<RequiredCharacteristicNotEmpty, CharacteristicValueRequiredProvider> {

    public boolean isValid(CharacteristicValueRequiredProvider characteristic, ConstraintValidatorContext context) {
        return !characteristic.getValue().isEmpty() || !characteristic.isRequired();
    }
}