package ru.reksoft.onlineShop.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ItemConverter {
    private CategoryRepository categoryRepository;

    @Autowired
    public ItemConverter(CategoryRepository categoryRepository, ItemRepository itemRepository) {
        this.categoryRepository = categoryRepository;
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
                .category(DtoToEntity.toEntity(itemDto.getCategory()))
                .characteristicValueMap(itemDto.getCharacteristicValueMap().entrySet()
                        .stream().collect((Collectors.toMap(x -> DtoToEntity.toEntity(x.getKey()),
                                Map.Entry::getValue))))
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
