package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.dto.UserDto;
import ru.reksoft.onlineShop.domain.repository.UserRepository;
import ru.reksoft.onlineShop.domain.util.EntityToDto;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public UserDto getById(long id){
        return EntityToDto.toDto(userRepository.getOne(id));
    }
}
