package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.converter.ItemConverter;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.dto.EditableItemDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.domain.converter.DtoToEntity;
import ru.reksoft.onlineShop.domain.converter.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private ItemRepository itemRepository;
    private ItemConverter itemConverter;


    @Autowired
    public ItemService(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter=itemConverter;
    }

    public List<ItemDto> getAll() {
        List<ItemEntity> itemEntities=itemRepository.findAll();
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

    public long add(EditableItemDto editableItemDto) {
        ItemEntity itemEntity=itemConverter.toEntity(editableItemDto);
        itemEntity.setId(itemRepository.count()+1);
       ItemEntity itemEntity1= itemRepository.save(itemEntity);
        return itemEntity1.getId();
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }
}
