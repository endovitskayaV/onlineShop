package ru.reksoft.onlineShop.domain.util;

import ru.reksoft.onlineShop.domain.dto.CategoryDto;
import ru.reksoft.onlineShop.domain.dto.CharacteristicDto;
import ru.reksoft.onlineShop.domain.dto.ItemDto;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;

import java.util.stream.Collectors;

public class EntityToDto {
    public static ItemDto toDto(ItemEntity itemEntity) {
        if (itemEntity == null) return null;
        return ItemDto.builder()
                .id(itemEntity.getId())
                .name(itemEntity.getName())
                .storage(itemEntity.getStorage())
                .price(itemEntity.getPrice())
                .categoryDto(toDto(itemEntity.getCategoryEntity()))
                .characteristicDtoList(
                        itemEntity.getCharacteristicEntityList()
                        .stream()
                        .map(EntityToDto::toDto).collect(Collectors.toList()))
                .build();
    }


    public static CharacteristicDto toDto(CharacteristicEntity characteristicEntity) {
        if (characteristicEntity == null) return null;
        return CharacteristicDto.builder()
                .id(characteristicEntity.getId())
                .name(characteristicEntity.getName())
                .type(characteristicEntity.getType())
                .categoryDtoList(characteristicEntity.getCategoryEntityList().stream()
                        .map(EntityToDto::toDto).collect(Collectors.toList()))
                .build();
    }

    public static CategoryDto toDto(CategoryEntity categoryEntity) {
        if (categoryEntity == null) return null;
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .rating(categoryEntity.getRating())
                .build();
    }
}
