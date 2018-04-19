package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.controller.util.CreateCharacteristic;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.domain.entity.DataType;
import ru.reksoft.onlineShop.service.validating.characteristicValueDataType.CharacteristicDataTypeValueProvider;
import ru.reksoft.onlineShop.service.validating.characteristicValueDataType.CheckCharacteristicValueDataType;
import ru.reksoft.onlineShop.service.validating.characteristicRequiredValue.CharacteristicValueRequiredProvider;
import ru.reksoft.onlineShop.service.validating.characteristicRequiredValue.RequiredCharacteristicNotEmpty;

import javax.validation.constraints.NotBlank;

/**
 * Data transfer object for CharacteristicEntity
 *
 * @see CharacteristicEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@RequiredCharacteristicNotEmpty //ensures required value filled in
@CheckCharacteristicValueDataType
public class CharacteristicDto implements CharacteristicValueRequiredProvider, CharacteristicDataTypeValueProvider {
    private long id;

    @NotBlank(message = "Name must contain at least one not blank character")
    private String name;

    /**
     * eg: metres, gramms, etc
     */
    @NotBlank(groups = CreateCharacteristic.class, message = "Type must contain at least one not blank character")
    private String measureUnit;

    private String value;
    private boolean required;

    /**
     * whether characterisitc value is a String, fractional number or integer number
     */
    private DataType valueDataType;
}
