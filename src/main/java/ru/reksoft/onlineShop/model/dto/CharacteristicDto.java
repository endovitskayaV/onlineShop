package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;

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
public class CharacteristicDto {
    private long id;
    private String name;

    /**
     * eg: metres, gramms, etc
     */
    private String type;
    private String value;
}
