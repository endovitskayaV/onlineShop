package ru.reksoft.onlineShop.domain.util;

import ru.reksoft.onlineShop.domain.dto.CategoryDto;
import ru.reksoft.onlineShop.domain.dto.CharacteristicDto;
import ru.reksoft.onlineShop.domain.dto.ItemDto;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;

import java.util.stream.Collectors;

public class DtoToEntity {
    public static ItemEntity toEntity(ItemDto itemDto) {
        if (itemDto == null) return null;
        return ItemEntity.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .storage(itemDto.getStorage())
                .price(itemDto.getPrice())
                .categoryEntity(toEntity(itemDto.getCategoryDto()))
                .characteristicEntityList(
                        itemDto.getCharacteristicDtoList().stream()
                                .map(DtoToEntity::toEntity).collect(Collectors.toList()))
                .build();
    }


    public static CharacteristicEntity toEntity(CharacteristicDto characteristicDto) {
        if (characteristicDto == null) return null;
        return CharacteristicEntity.builder()
                .id(characteristicDto.getId())
                .name(characteristicDto.getName())
                .type(characteristicDto.getType())
                .categoryEntityList(characteristicDto.getCategoryDtoList().stream()
                        .map(DtoToEntity::toEntity).collect(Collectors.toList()))
                .build();
    }

    public static CategoryEntity toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .rating(categoryDto.getRating())
                .build();
    }
}
