package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.dto.CharacteristicDto;
import ru.reksoft.onlineShop.domain.repository.CharacteristicRepository;
import ru.reksoft.onlineShop.domain.converter.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacteristicService {
    private CharacteristicRepository characteristicRepository;
    private ItemRepository itemRepository;

    @Autowired
    public CharacteristicService(CharacteristicRepository characteristicRepository, ItemRepository itemRepository){
        this.characteristicRepository = characteristicRepository;
        this.itemRepository=itemRepository;
    }

    public List<CharacteristicDto> getAll(){
        return characteristicRepository.findAll().stream()
                .map(EntityToDto::toDto).collect(Collectors.toList());
    }


    public CharacteristicDto getById(long id){
        CharacteristicEntity characteristicEntity=characteristicRepository.findById(id).orElse(null);
        return EntityToDto.toDto(characteristicRepository.findById(id).orElse(null));
    }

}
