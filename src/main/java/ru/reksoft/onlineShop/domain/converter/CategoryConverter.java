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
import java.util.stream.Collectors;

@Service
public class CategoryConverter {
    private CategoryRepository categoryRepository;
    private CharacteristicRepository characteristicRepository;

    @Autowired
    public CategoryConverter(CategoryRepository categoryRepository, CharacteristicRepository characteristicRepository) {
        this.categoryRepository = categoryRepository;
        this.characteristicRepository = characteristicRepository;
    }

    public CategoryEntity toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .rating(categoryDto.getRating())
                .characteristicRequiredMap(categoryDto.getCharacteristicRequiredMap().entrySet()
                        .stream().collect((Collectors.toMap(x -> DtoToEntity.toEntity(x.getKey()), Map.Entry::getValue))))
                .build();
    }


    public CategoryEntity toEntity(NewCategoryDto newCategoryDto) {
        if (newCategoryDto == null) {
            return null;
        } else {
            Map<CharacteristicEntity, Boolean> map = new HashMap<>();
            newCategoryDto.getCharacteristicIds()
                    .forEach(x -> map
                            .put(characteristicRepository.findById(x).orElse(null), true));
            return CategoryEntity.builder()
                    .name(newCategoryDto.getName())
                    .description(newCategoryDto.getDescription())
                    .rating(newCategoryDto.getRating())
                    .characteristicRequiredMap(map)
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
                .characteristicRequiredMap(categoryEntity.getCharacteristicRequiredMap().entrySet()
                        .stream().collect(Collectors.toMap(x ->
                                        EntityToDto.toDto(x.getKey()),
                                Map.Entry::getValue)))
                .build();
    }

}
