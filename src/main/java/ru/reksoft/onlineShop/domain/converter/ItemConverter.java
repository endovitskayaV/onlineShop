package ru.reksoft.onlineShop.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemConverter {
    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;
    private CharacteristicConverter characteristicConverter;

    @Autowired
    public ItemConverter(CategoryRepository categoryRepository,
                         CategoryConverter categoryConverter,
                         CharacteristicConverter characteristicConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.characteristicConverter = characteristicConverter;
    }

    public ItemDto toDto(ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        } else {
            List<CharacteristicDto> characteristicDtos = itemEntity.getCharacteristicValue()
                    .keySet().stream()
                    .map(characteristicConverter::toDto)
                    .collect(Collectors.toList());
            List<String> values = new ArrayList<>(itemEntity.getCharacteristicValue().values());
            for (int i = 0; i < values.size(); i++) {
                characteristicDtos.get(i).setValue(values.get(i));
            }
            return ItemDto.builder()
                    .id(itemEntity.getId())
                    .name(itemEntity.getName())
                    .producer(itemEntity.getProducer())
                    .description(itemEntity.getDescription())
                    .storage(itemEntity.getStorage())
                    .price(itemEntity.getPrice())
                    .categoryId(categoryConverter.toDto(itemEntity.getCategory()).getId())
                    .characteristics(characteristicDtos)
                    .build();
        }
    }

    public ItemEntity toEntity(ItemDto itemDto) {
        if (itemDto == null) {
            return null;
        } else {
            Map<CharacteristicEntity, String> map = new HashMap<>();
            itemDto.setCharacteristics(
                    itemDto.getCharacteristics().stream()
                            .filter(characteristicDto -> !characteristicDto.getValue().equals(""))
                            .collect(Collectors.toList()));
            itemDto.getCharacteristics().forEach(
                    characteristicDto -> map.put(characteristicConverter.toEntity(characteristicDto),
                            characteristicDto.getValue()));
            return ItemEntity.builder()
                    .id(itemDto.getId())
                    .name(itemDto.getName())
                    .producer(itemDto.getProducer())
                    .description(itemDto.getDescription())
                    .storage(itemDto.getStorage())
                    .price(itemDto.getPrice())
                    .category(categoryRepository.findById(itemDto.getCategoryId()).orElse(null))
                    .characteristicValue(map)
                    .build();
        }
    }
}
