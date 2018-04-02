package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.dto.StatusDto;
import ru.reksoft.onlineShop.domain.repository.StatusRepository;
import ru.reksoft.onlineShop.domain.util.EntityToDto;

@Service
public class StatusService {
    private StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository){
        this.statusRepository=statusRepository;
    }

    public StatusDto getById(long id){
        return EntityToDto.toDto(statusRepository.getOne(id));
    }
}
