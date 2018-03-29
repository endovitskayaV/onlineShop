package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.dto.ItemDto;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.domain.util.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private ItemRepository itemRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository){
        this.itemRepository=itemRepository;
    }

    public List<ItemDto> getAll(){
        return  itemRepository.findAll()
                .stream()
                .map(EntityToDto::toDto).collect(Collectors.toList());
    }
}
