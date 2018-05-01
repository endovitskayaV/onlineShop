package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.controller.util.SortCriteria;
import ru.reksoft.onlineShop.model.converter.ItemConverter;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicValueDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.ValueChecked;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Interacts with repositories to extract required objects
 * converts extracted entities to dtos
 */
@Service
public class ItemService {
    private ItemRepository itemRepository;
    private ItemConverter itemConverter;

    /**
     * @param itemRepository repository for item
     * @param itemConverter  converter for item
     */
    @Autowired
    public ItemService(ItemRepository itemRepository, ItemConverter itemConverter) {
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    /**
     * Gets all items stored in database
     *
     * @return all item dtos
     */
    private List<ItemDto> getAll() {
        return itemRepository.findAll(Sort.by(Sort.Direction.DESC, SortCriteria.POPULARITY.name().toLowerCase()))
                .stream()
                .map(itemConverter::toDto)
                .collect(Collectors.toList());
    }

    /**_
     * @param id item id
     * @return item dto found by its id
     */
    public ItemDto getById(long id) {
        return itemConverter.toDto(itemRepository.findById(id).orElse(null));
    }

    public List<ItemDto> getByCategoryId(long categoryId, boolean isAscSort, SortCriteria sortCriteria) {
        return sortCriteria == null ?
                getByCategoryId(categoryId) :
                itemRepository.findAllByCategoryId(categoryId, Sort.by(getDirection(isAscSort), sortCriteria.name().toLowerCase()))
                        .stream()
                        .map(itemConverter::toDto).collect(Collectors.toList());
    }

    public List<ItemDto> getByCharacteristic(long categoryId, Map<String, List<String>> filterCharacteristics,
                                             boolean isAcsSort, SortCriteria sortCriteria) {
        List<ItemDto> foundItems = new ArrayList<>();

        getByCategoryId(categoryId, isAcsSort, sortCriteria).forEach(itemDto -> {

            if (itemDto.getCharacteristics().stream().anyMatch(characteristicDto ->

                    (filterCharacteristics.keySet().contains(characteristicDto.getCode()) &&
                            filterCharacteristics.get(characteristicDto.getCode()).contains(characteristicDto.getValue()))))
                foundItems.add(itemDto);
        });
        return foundItems;
    }


    /**
     * @param categoryId category id
     * @return item dtos having given categoryId
     */
    private List<ItemDto> getByCategoryId(long categoryId) {
        return itemRepository.findAllByCategoryIdOrderByPopularity(categoryId).stream()
                .map(itemConverter::toDto).collect(Collectors.toList());
    }

    public List<CharacteristicValueDto> getCharacteristicValues(long categoryId) {
        List<CharacteristicValueDto> characteristicValueDtos = new ArrayList<>();

        itemRepository.findAllByCategoryId(categoryId).forEach(itemEntity -> {
            itemEntity.getCharacteristicValue().values().forEach(charactersticValueEntity -> {
                if (characteristicValueDtos.stream()
                        .anyMatch(characteristicValueDto ->
                                characteristicValueDto.getCode().equals(charactersticValueEntity.getCharacteristic().getCode()))) {
                    CharacteristicValueDto foundCharacteristicValueDto =
                            characteristicValueDtos.get(characteristicValueDtos.indexOf((characteristicValueDtos.stream()
                                    .filter(characteristicValueDto -> characteristicValueDto
                                            .getCode().equals(charactersticValueEntity.getCharacteristic().getCode()))
                                    .findFirst().get())));

                    List<ValueChecked> values = foundCharacteristicValueDto.getValues();
                    values.add(new  ValueChecked(charactersticValueEntity.getValue(), false));
                    foundCharacteristicValueDto.setValues(values);
                } else {
                    List<ValueChecked> values = new ArrayList<>();
                    values.add(new  ValueChecked(charactersticValueEntity.getValue(), false));
                    characteristicValueDtos.add(new CharacteristicValueDto(
                            charactersticValueEntity.getCharacteristic().getName(), charactersticValueEntity.getCharacteristic().getCode(), values));
                }
            });
        });

        return characteristicValueDtos;
    }

    private Sort.Direction getDirection(boolean isAcsSort) {
        return isAcsSort ? Sort.Direction.ASC : Sort.Direction.DESC;
    }

    public List<ItemDto> getByNameOrProducer(String query, boolean isAcsSort, SortCriteria sortCriteria) {
        return sortCriteria == null ?
                itemRepository.findAllByNameContainsOrProducerContains(
                        query, query, Sort.by(Sort.Direction.ASC, SortCriteria.PRODUCER.name().toLowerCase())).stream()
                        .map(itemConverter::toDto).collect(Collectors.toList()) :

                itemRepository.findAllByNameContainsOrProducerContains(
                        query, query, Sort.by(getDirection(isAcsSort), sortCriteria.name().toLowerCase())).stream()
                        .map(itemConverter::toDto).collect(Collectors.toList());
    }

    public List<ItemDto> getAll(boolean isAcsSort, SortCriteria sortCriteria) {
        return sortCriteria == null ?
                getAll() :
                itemRepository.findAll(Sort.by(getDirection(isAcsSort), sortCriteria.name().toLowerCase()))
                        .stream()
                        .map(itemConverter::toDto).collect(Collectors.toList());
    }


    public List<ItemDto> getByQueryAndCategoryId(String query, long categoryId, boolean isAcsSort, SortCriteria sortCriteria) {
        return itemRepository.findAllByNameContainsOrProducerContains(
                query, query, Sort.by(getDirection(isAcsSort), sortCriteria.name().toLowerCase())).stream()
                .map(itemConverter::toDto)
                .collect(Collectors.toList())
                .stream().filter(itemDto -> itemDto.getCategoryId() == categoryId)
                .collect(Collectors.toList());
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
