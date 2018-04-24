package ru.reksoft.onlineShop.model.converter;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.CharacteristicValueDto;
import ru.reksoft.onlineShop.service.CharacteristicService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Converts CharacteristicEntity to Characteristic dtos and v.v
 *
 * @see CharacteristicEntity
 * @see CharacteristicDto
 */

@Service
public class CharacteristicConverter {
    private CharacteristicRepository characteristicRepository;

    public CharacteristicConverter(CharacteristicRepository characteristicRepository) {
        this.characteristicRepository = characteristicRepository;
    }

    /**
     * Converts CharacteristicEntity to CharacteristicDto
     *
     * @param characteristicEntity {@link CharacteristicEntity}
     * @return characteristicDto converted from characteristicEntity or null
     */
    public CharacteristicDto toDto(CharacteristicEntity characteristicEntity, boolean required) {
        if (characteristicEntity == null) {
            return null;
        } else {
            return CharacteristicDto.builder()
                    .id(characteristicEntity.getId())
                    .name(characteristicEntity.getName())
                    .measureUnit(characteristicEntity.getMeasureUnit())
                    .required(required)
                    .valueDataType(characteristicEntity.getValueDataType())
                    .code(characteristicEntity.getCode())
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
                    .measureUnit(characteristicDto.getMeasureUnit())
                    .valueDataType(characteristicDto.getValueDataType())
                    .code(characteristicDto.getCode())
                    .build();
        }
    }

}