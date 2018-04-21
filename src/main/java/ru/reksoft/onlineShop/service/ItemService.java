package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.controller.util.SortCriteria;
import ru.reksoft.onlineShop.model.domain.converter.ItemConverter;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.model.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.dto.CharacterisricValueDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interacts with repositories to extract required objects
 * converts extracted entities to dtos
 */
@Service
public class ItemService {
    private ItemRepository itemRepository;
    private ItemConverter itemConverter;
    private CharacteristicRepository characteristicRepository;

    /**
     * @param itemRepository repository for item
     * @param itemConverter  converter for item
     */
    @Autowired
    public ItemService(ItemRepository itemRepository, ItemConverter itemConverter,CharacteristicRepository characteristicRepository) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
        this.characteristicRepository=characteristicRepository;
    }

    /**
     * Gets all items stored in database
     *
     * @return all item dtos
     */
    public List<ItemDto> getAll() {
        return itemRepository.findAll()
                .stream()
                .map(itemConverter::toDto)
                .collect(Collectors.toList());
    }

    /**
     * @param id item id
     * @return item dto found by its id
     */
    public ItemDto getById(long id) {
        return itemConverter.toDto(itemRepository.findById(id).orElse(null));
    }

    /**
     * @param categoryId category id
     * @return item dtos having given categoryId
     */
    public List<ItemDto> getByCategoryId(long categoryId) {
        return itemRepository.findAllByCategoryIdOrderByProducer(categoryId).stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }

    public List<ItemDto> getByCategoryId(long categoryId, boolean isAscSort, SortCriteria sortCriteria) {
        if (sortCriteria==null) return getByCategoryId(categoryId);
        Sort.Direction direction;
        if (isAscSort) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        return itemRepository.findAllByCategoryId(categoryId, Sort.by(direction, sortCriteria.name().toLowerCase()))
                .stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }


    public List<ItemDto> getByCharacteristic(long categoryId, List<CharacterisricValueDto>characterisricValueDtos) {
    //    characteristicRepository.

        return itemRepository.findAllByCategoryIdOrderByProducer(categoryId)
                .stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }



    public List<ItemDto> getByNameOrProducer(String query) {
        return itemRepository.findAllByNameContainsOrProducerContainsOrderByProducer(query, query).stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }


    public List<ItemDto> getAll(boolean isAcsSort, SortCriteria sortCriteria) {
        Sort.Direction direction;
        if (isAcsSort) {
            direction = Sort.Direction.ASC;
        } else {
            direction = Sort.Direction.DESC;
        }
        return itemRepository.findAll(Sort.by(direction, sortCriteria.name().toLowerCase()))
                .stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }

    /**
     * Updates given item
     *
     * @param itemDto item that should be updated
     * @return edited item id
     */
    public long edit(ItemDto itemDto) {
        return itemRepository.save(itemConverter.toEntity(itemDto)).getId();
    }

    /**
     * Inserts to database new item
     *
     * @param itemDto item dto that should be inserted to database
     * @return id of created item
     */
    public long add(ItemDto itemDto) {
        if (itemRepository.findByNameAndProducer(itemDto.getName(), itemDto.getProducer()) != null) {
            return -1;
        } else {
            ItemEntity newItemEntity = itemConverter.toEntity(itemDto);
            newItemEntity.setId(itemRepository.count() + 1); //generate id
            return itemRepository.save(newItemEntity).getId();
        }
    }

    /**
     * Deletes given item from database
     *
     * @param id item id
     * @return whether item can be deleted
     */
    public boolean delete(long id) {
        try {
            itemRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
