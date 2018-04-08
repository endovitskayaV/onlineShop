package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.converter.CharacteristicConverter;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.domain.repository.CharacteristicRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacteristicService {
    private CharacteristicRepository characteristicRepository;
    private CharacteristicConverter characteristicConverter;

    @Autowired
    public CharacteristicService(CharacteristicRepository characteristicRepository,
                                 CharacteristicConverter characteristicConverter) {
        this.characteristicRepository = characteristicRepository;
        this.characteristicConverter = characteristicConverter;
    }

    public List<CharacteristicDto> getAll() {
        return characteristicRepository.findAll().stream()
                .map(characteristicConverter::toDto).collect(Collectors.toList());
    }

}
