package ru.reksoft.onlineShop.service.validating.characteristicRequiredValue;

public interface CharacteristicValueRequiredProvider {

    boolean isRequired();
    String getValue();
}
