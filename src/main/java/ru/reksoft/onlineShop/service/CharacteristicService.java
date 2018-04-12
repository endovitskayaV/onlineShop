package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.converter.CharacteristicConverter;
import ru.reksoft.onlineShop.model.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.model.domain.repository.CharacteristicRepository;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interacts with repositories to extract required objects
 * converts extracted entities to dtos
 */
@Service
public class CharacteristicService {
    private CharacteristicRepository characteristicRepository;
    private CharacteristicConverter characteristicConverter;
    private CategoryRepository categoryRepository;

    /**
     * @param characteristicRepository repository for characteristic
     * @param characteristicConverter  converter for characteristic
     */
    @Autowired
    public CharacteristicService(CharacteristicRepository characteristicRepository,
                                 CharacteristicConverter characteristicConverter,
                                 CategoryRepository categoryRepository) {
        this.characteristicRepository = characteristicRepository;
        this.characteristicConverter = characteristicConverter;
        this.categoryRepository=categoryRepository;
    }

    /**
     * Gets all characteristics stored in database
     *
     * @return all characteristic dtos
     */
    public List<CharacteristicDto> getAll() {
        return characteristicRepository.findAll().stream()
                .map(characteristicEntity ->
                        characteristicConverter
                                .toDto(characteristicEntity, false))
                .collect(Collectors.toList());
    }


    public List<CharacteristicDto> getAllByCategoryId(long categoryId) {
        return characteristicRepository.findAll().stream()
                .map(characteristicEntity ->
                        characteristicConverter
                                .toDto(characteristicEntity,
                                        categoryRepository.findById(categoryId).orElse(null)
                                                .getCharacteristicsRequired().get(characteristicEntity)))
                .collect(Collectors.toList());
    }

}
