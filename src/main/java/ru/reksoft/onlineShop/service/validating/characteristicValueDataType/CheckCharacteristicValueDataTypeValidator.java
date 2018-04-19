package ru.reksoft.onlineShop.service.validating.characteristicValueDataType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckCharacteristicValueDataTypeValidator implements ConstraintValidator<CheckCharacteristicValueDataType, CharacteristicDataTypeValueProvider> {

    public boolean isValid(CharacteristicDataTypeValueProvider characteristic, ConstraintValidatorContext context) {
        switch (characteristic.getValueDataType()) {
            case STRING:
                return true;
            case INTEGER:
                try {
                    Integer.parseInt(characteristic.getValue());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }

            case FRACTIONAL:
                try {
                    Double.parseDouble(characteristic.getValue());
                    return true;
                } catch (NumberFormatException e) {
                    return false;
                }
        }
        return false;
    }
}
