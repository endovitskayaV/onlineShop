package ru.reksoft.onlineShop.model.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.domain.entity.CharactersticValueEntity;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.domain.repository.CharactersticValueRepository;
import ru.reksoft.onlineShop.model.domain.repository.SellerRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.dto.ItemDto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Converts ItemEntity to Item dtos and v.v
 *
 * @see ItemEntity
 * @see ItemDto
 */
@Service
public class ItemConverter {
    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;
    private CharacteristicConverter characteristicConverter;
    private CharactersticValueRepository charactersticValueRepository;
    private SellerRepository sellerRepository;

    /**
     * @param categoryRepository      repository for category
     * @param categoryConverter       converter for category
     * @param characteristicConverter converter for characteristic
     */
    @Autowired
    public ItemConverter(CategoryRepository categoryRepository,
                         CategoryConverter categoryConverter,
                         CharacteristicConverter characteristicConverter,
                         CharactersticValueRepository charactersticValueRepository,
                         SellerRepository sellerRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.characteristicConverter = characteristicConverter;
        this.charactersticValueRepository = charactersticValueRepository;
        this.sellerRepository = sellerRepository;
    }

    /**
     * Converts ItemEntity to ItemDto
     *
     * @param itemEntity entity that will be converted
     * @return itemDto converted from itemEntity
     */
    public ItemDto toDto(ItemEntity itemEntity) {
        if (itemEntity == null) {
            return null;
        } else {
            //construct characteristicDtoList for entityDto
            List<CharacteristicDto> characteristicDtos = itemEntity.getCharacteristicValue()
                    .keySet().stream() //key is characteristic
                    .map(characteristicEntity -> characteristicConverter
                            .toDto(characteristicEntity,
                                    itemEntity.getCategory().getCharacteristicsRequired().get(characteristicEntity)))
                    .collect(Collectors.toList());

            List<String> values = new ArrayList<>();
            itemEntity.getCharacteristicValue().values().forEach(charactersticValueEntity -> values.add(charactersticValueEntity.getValue()));
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
                    .photoNameOriginal(itemEntity.getPhotoNameOriginal())
                    .photoNameCompressed(itemEntity.getPhotoNameCompressed())
                    .sellerId(itemEntity.getSeller().getUserId())
                    .build();
        }
    }

    /**
     * Converts ItemDto to ItemEntity
     *
     * @param itemDto dto that will be converted
     * @return itemEntity converted from itemDto
     */
    public ItemEntity toEntity(ItemDto itemDto) {
        if (itemDto == null) {
            return null;
        } else {
            //do not select characteristics with empty value
            itemDto.setCharacteristics(
                    itemDto.getCharacteristics().stream()
                            .filter(characteristicDto -> !characteristicDto.getValue().equals(""))
                            .collect(Collectors.toList()));

            //construct characteristicsValues for itemEntity
            Map<CharacteristicEntity, CharactersticValueEntity> characteristicsValues = new HashMap<>();
            itemDto.getCharacteristics().forEach(
                    characteristicDto -> {
                        CharacteristicEntity characteristicEntity = characteristicConverter.toEntity(characteristicDto);

                        CharactersticValueEntity charactersticValueEntity = charactersticValueRepository.findByCharacteristicIdAndValue(characteristicDto.getId(), characteristicDto.getValue());
                        if (charactersticValueEntity == null) {
                            charactersticValueEntity = CharactersticValueEntity.builder()
                                    .characteristic(characteristicEntity)
                                    .id(charactersticValueRepository.count() + 1)
                                    .value(characteristicDto.getValue())
                                    .build();
                            charactersticValueRepository.save(charactersticValueEntity);
                        }
                        characteristicsValues.put(characteristicEntity, charactersticValueEntity);
                    });

            return ItemEntity.builder()
                    .id(itemDto.getId())
                    .name(itemDto.getName())
                    .producer(itemDto.getProducer())
                    .description(itemDto.getDescription())
                    .storage(itemDto.getStorage())
                    .price(itemDto.getPrice())
                    .category(categoryRepository.findById(itemDto.getCategoryId()).orElse(null))
                    .characteristicValue(characteristicsValues)
                    .photoNameOriginal(itemDto.getPhotoNameOriginal())
                    .photoNameCompressed(itemDto.getPhotoNameCompressed())
                    .seller(sellerRepository.getOne(itemDto.getSellerId()))
                    .build();
        }
    }


}
