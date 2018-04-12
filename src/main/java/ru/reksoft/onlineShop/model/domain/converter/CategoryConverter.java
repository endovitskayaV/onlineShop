package ru.reksoft.onlineShop.model.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converts CategoryEntity to Category dtos and v.v
 *
 * @see CategoryEntity
 * @see CategoryDto
 * @see NewCategoryDto
 */
@Service
public class CategoryConverter {
    private CharacteristicRepository characteristicRepository;
    private CharacteristicConverter characteristicConverter;

    /**
     * @param characteristicRepository repository for CharacteristicEntity
     * @param characteristicConverter  converter for Characteristic
     */
    @Autowired
    public CategoryConverter(CharacteristicRepository characteristicRepository,
                             CharacteristicConverter characteristicConverter) {
        this.characteristicRepository = characteristicRepository;
        this.characteristicConverter = characteristicConverter;
    }

    /**
     * Converts NewCategoryDto to CategoryEntity
     *
     * @param newCategoryDto category dto that is used while making new category
     * @return categoryEntity converted from newCategoryDto or null
     */
    public CategoryEntity toEntity(NewCategoryDto newCategoryDto) {
        if (newCategoryDto == null) {
            return null;
        } else {
            //construct characteristicRequiredMap from characteristic ids
            Map<CharacteristicEntity, Boolean> characteristicRequired = new HashMap<>();
            newCategoryDto.getCharacteristicIds()
                    .forEach(characteristicId ->
                            characteristicRequired.put(
                                    characteristicRepository.findById(characteristicId)
                                            .orElse(null), true));
            return CategoryEntity.builder()
                    .name(newCategoryDto.getName())
                    .description(newCategoryDto.getDescription())
                    .rating(newCategoryDto.getRating())
                    .characteristicsRequired(characteristicRequired)
                    .build();
        }
    }

    /**
     * Converts CategoryEntity to CategoryDto
     *
     * @param categoryEntity {@link CategoryEntity}
     * @return categoryDto converted from categoryEntity or null
     */
    public CategoryDto toDto(CategoryEntity categoryEntity) {
        if (categoryEntity == null) {
            return null;
        } else {
            return CategoryDto.builder()
                    .id(categoryEntity.getId())
                    .name(categoryEntity.getName())
                    .description(categoryEntity.getDescription())
                    .rating(categoryEntity.getRating())
                    .characteristics(categoryEntity.getCharacteristicsRequired()//.entrySet().stream()
                            // .filter(Map.Entry::getValue) //get only required characteristics (value==true)
                            // .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                            .keySet().stream() //select keys - characteristics
                            .map(characteristicEntity ->
                                    characteristicConverter
                                            .toDto(characteristicEntity,
                                                    categoryEntity.getCharacteristicsRequired().get(characteristicEntity)))
                            .collect(Collectors.toList()))
                    .build();
        }
    }
}
