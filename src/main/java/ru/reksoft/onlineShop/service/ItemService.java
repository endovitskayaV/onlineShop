package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.converter.ItemConverter;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private ItemConverter itemConverter;


    @Autowired
    public ItemService(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    public List<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(itemConverter::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto getById(long id) {
        return itemConverter.toDto(itemRepository.findById(id).orElse(null));
    }

    public List<ItemDto> getByCategoryId(long categoryId) {
        return itemRepository.findAllByCategoryId(categoryId).stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }

    public long edit(ItemDto itemDto) {
//        if (itemRepository.findById(editableItemDto.getCategoryId()).orElse(null) != null)
//            return itemRepository.save(itemConverter.toEntity(editableItemDto)).getId();
//        ItemEntity itemEntity = itemConverter.toEntity(editableItemDto);
//        itemEntity.setId(itemRepository.count() + 1);
//        ItemEntity itemEntity1 = itemRepository.save(itemEntity);
//
//        return itemEntity1.getId();
        return 1;
    }

    public long add(ItemDto itemDto) {
        if (itemRepository.
                findByNameAndProducer(itemDto.getName(),
                        itemDto.getProducer()) != null) {
            return -1;
        }
        ItemEntity newItemEntity = itemConverter.toEntity(itemDto);
        newItemEntity.setId(itemRepository.count() + 1);
        return itemRepository.save(newItemEntity).getId();
    }

    public boolean delete(long id) {
        try {
            itemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
