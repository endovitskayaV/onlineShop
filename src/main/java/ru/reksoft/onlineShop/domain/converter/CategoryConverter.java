package ru.reksoft.onlineShop.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class CategoryConverter {
    private CharacteristicRepository characteristicRepository;
    private CharacteristicConverter characteristicConverter;

    @Autowired
    public CategoryConverter(CharacteristicRepository characteristicRepository,
                             CharacteristicConverter characteristicConverter) {
        this.characteristicRepository = characteristicRepository;
        this.characteristicConverter = characteristicConverter;
    }

    public CategoryEntity toEntity(NewCategoryDto newCategoryDto) {
        if (newCategoryDto == null) {
            return null;
        } else {
            Map<CharacteristicEntity, Boolean> map = new HashMap<>();
            newCategoryDto.getCharacteristicIds()
                    .forEach(characteristicId -> map
                            .put(characteristicRepository.findById(characteristicId)
                                    .orElse(null), true));
            return CategoryEntity.builder()
                    .name(newCategoryDto.getName())
                    .description(newCategoryDto.getDescription())
                    .rating(newCategoryDto.getRating())
                    .characteristicRequired(map)
                    .build();
        }
    }

    public CategoryDto toDto(CategoryEntity categoryEntity) {
        if (categoryEntity == null) return null;
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .rating(categoryEntity.getRating())
                .characteristics(categoryEntity.getCharacteristicRequired().entrySet().stream()
                        .filter(Map.Entry::getValue)
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                        .keySet().stream()
                        .map(characteristicConverter::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
