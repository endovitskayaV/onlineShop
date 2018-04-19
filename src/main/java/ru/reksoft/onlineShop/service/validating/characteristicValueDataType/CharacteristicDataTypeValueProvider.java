package ru.reksoft.onlineShop.service.validating.characteristicValueDataType;

import ru.reksoft.onlineShop.model.domain.entity.DataType;

public interface CharacteristicDataTypeValueProvider {
    DataType getValueDataType();
    String getValue(); //fgh
}
