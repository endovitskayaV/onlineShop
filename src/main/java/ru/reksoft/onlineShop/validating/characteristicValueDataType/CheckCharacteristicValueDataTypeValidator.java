package ru.reksoft.onlineShop.validating.characteristicValueDataType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCharacteristicValueDataTypeValidator implements ConstraintValidator<CheckCharacteristicValueDataType, CharacteristicDataTypeValueProvider> {

    public boolean isValid(CharacteristicDataTypeValueProvider characteristic, ConstraintValidatorContext context) {
        if (characteristic.getValue().isEmpty()) {
            return true;
        } else {
            switch (characteristic.getValueDataType()) {
                case STRING:
                    return true;
                case INTEGER:
                    try {
                        Integer.parseInt(characteristic.getValue());
                        return true;
                    } catch (NumberFormatException e) {
                        context.buildConstraintViolationWithTemplate("Enter integer number").addConstraintViolation();
                        return false;
                    }

                case FRACTIONAL:
                    try {
                        Double.parseDouble(characteristic.getValue());
                        return true;
                    } catch (NumberFormatException e) {
                        context.buildConstraintViolationWithTemplate("Enter number").addConstraintViolation();
                        return false;
                    }
            }
            return false;
        }
    }
}
