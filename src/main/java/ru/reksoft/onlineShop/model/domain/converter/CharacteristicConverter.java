package ru.reksoft.onlineShop.model.domain.converter;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;

/**
 * Converts CharacteristicEntity to Characteristic dtos and v.v
 *
 * @see CharacteristicEntity
 * @see CharacteristicDto
 */
@NoArgsConstructor
@Service
public class CharacteristicConverter {

    /**
     * Converts CharacteristicEntity to CharacteristicDto
     *
     * @param characteristicEntity {@link CharacteristicEntity}
     * @return characteristicDto converted from characteristicEntity or null
     */
    public CharacteristicDto toDto(CharacteristicEntity characteristicEntity) {
        if (characteristicEntity == null) {
            return null;
        } else {
            return CharacteristicDto.builder()
                    .id(characteristicEntity.getId())
                    .name(characteristicEntity.getName())
                    .type(characteristicEntity.getType())
                    .build();
        }
    }

    /**
     * Converts CharacteristicDto to CharacteristicEntity
     *
     * @param characteristicDto {@link CharacteristicDto}
     * @return characteristicEntity converted from characteristicDto or null
     */
    public CharacteristicEntity toEntity(CharacteristicDto characteristicDto) {
        if (characteristicDto == null) {
            return null;
        } else {
            return CharacteristicEntity.builder()
                    .id(characteristicDto.getId())
                    .name(characteristicDto.getName())
                    .type(characteristicDto.getType())
                    .build();
        }
    }
}
