package ru.reksoft.onlineShop.validating.characteristicValueDataType;

import ru.reksoft.onlineShop.model.dto.DataType;

public interface CharacteristicDataTypeValueProvider {
    DataType getValueDataType();
    String getValue();
}
