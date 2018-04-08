package ru.reksoft.onlineShop.domain.converter;

import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;

@Service
public class CharacteristicConverter {

    public CharacteristicConverter() {
    }

    public CharacteristicDto toDto(CharacteristicEntity characteristicEntity) {
        if (characteristicEntity == null) return null;

        return CharacteristicDto.builder()
                .id(characteristicEntity.getId())
                .name(characteristicEntity.getName())
                .type(characteristicEntity.getType())
                .build();
    }

    public CharacteristicEntity toEntity(CharacteristicDto characteristicDto) {
        if (characteristicDto == null) return null;
        return CharacteristicEntity.builder()
                .id(characteristicDto.getId())
                .name(characteristicDto.getName())
                .type(characteristicDto.getType())
                .build();
    }
}
