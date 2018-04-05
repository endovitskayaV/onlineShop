package ru.reksoft.onlineShop.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
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

    @Autowired
    public ItemConverter(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    public ItemDto toDto(ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        } else {
            List<CharacteristicDto> characteristicDtoList = itemEntity.getCharacteristicValueMap()
                    .keySet().stream()
                    .map(EntityToDto::toDto)
                    .collect(Collectors.toList());
            List<String> values = new ArrayList<>(itemEntity.getCharacteristicValueMap().values());
            for (int i = 0; i < values.size(); i++) {
                characteristicDtoList.get(i).setValue(values.get(i));
            }
            return ItemDto.builder()
                    .id(itemEntity.getId())
                    .name(itemEntity.getName())
                    .producer(itemEntity.getProducer())
                    .description(itemEntity.getDescription())
                    .storage(itemEntity.getStorage())
                    .price(itemEntity.getPrice())
                    .category(categoryConverter.toDto(itemEntity.getCategory()))
                    .characteristicList(characteristicDtoList)
                    .build();
        }
    }


    public ItemEntity toEntity(ItemDto itemDto) {
        if (itemDto == null) return null;
        return ItemEntity.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .storage(itemDto.getStorage())
                .price(itemDto.getPrice())
                .producer(itemDto.getProducer())
                .category(categoryConverter.toEntity(itemDto.getCategory()))
                .characteristicValueMap(itemDto.getCharacteristicList()
                        .stream().collect((Collectors.toMap(x ->
                                        (CharacteristicEntity
                                                .builder()
                                                .id(x.getId())
                                                .name(x.getName())
                                                .type(x.getType())
                                                .build()),
                                CharacteristicDto::getValue))))
                .build();

    }


    public ItemEntity toEntity(EditableItemDto editableItemDto) {
        if (editableItemDto == null) {
            return null;
        } else {
            Map<CharacteristicEntity, String> map = new HashMap<>();
            editableItemDto.getCharacteristicsList().forEach(
                    characteristicDto -> map.put(DtoToEntity.toEntity(characteristicDto), characteristicDto.getValue())
            );
            return ItemEntity.builder()
                    .name(editableItemDto.getName())
                    .producer(editableItemDto.getProducer())
                    .description(editableItemDto.getDescription())
                    .storage(editableItemDto.getStorage())
                    .price(editableItemDto.getPrice())
                    .category(categoryRepository.findById(editableItemDto.getCategoryId()).orElse(null))
                    .characteristicValueMap(map)
                    .build();
        }

    }

}
