package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.domain.util.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacteristicService {
    private CharacteristicRepository characteristicRepository;

    @Autowired
    public CharacteristicService(CharacteristicRepository characteristicRepository){
        this.characteristicRepository = characteristicRepository;
    }

    public List<CharacteristicDto> getAll(){
        return characteristicRepository.findAll().stream()
                .map(EntityToDto::toDto).collect(Collectors.toList());
    }
}
