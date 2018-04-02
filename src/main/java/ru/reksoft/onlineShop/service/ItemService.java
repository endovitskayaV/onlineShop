package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.domain.util.DtoToEntity;
import ru.reksoft.onlineShop.domain.util.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(EntityToDto::toDto)
                .collect(Collectors.toList());
    }

    public ItemDto getById(long id) {
        return EntityToDto.toDto(itemRepository.findById(id).orElse(null));
    }

    public List<ItemDto> getByCategoryId(long categoryId) {
        return itemRepository.findAllByCategoryId(categoryId).stream()
                .map(EntityToDto::toDto).collect(Collectors.toList());
    }

    public void add(ItemDto itemDto) {
        itemRepository.save(DtoToEntity.toEntity(itemDto));
    }

    public void delete(long id) {
        itemRepository.deleteById(id);
    }
}
