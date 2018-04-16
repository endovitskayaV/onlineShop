package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.controller.util.CreateCharacteristic;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.validator.Interface1;
import ru.reksoft.onlineShop.validator.RequiredCharacteristicNotEmpty;

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
public class CharacteristicDto implements Interface1 {
    private long id;

    @NotBlank(message = "Name must contain at least one not blank character")
    private String name;

    /**
     * eg: metres, gramms, etc
     */
    //TODO: implement groups
     @NotBlank(groups = CreateCharacteristic.class,message = "Type must contain at least one not blank character")
    private String type;

    private String value;
    private boolean required;
}
