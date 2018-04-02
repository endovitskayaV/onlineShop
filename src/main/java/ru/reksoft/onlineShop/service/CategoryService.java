package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.domain.util.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository=categoryRepository;
    }

    public List<CategoryDto> getAll(){
        return categoryRepository.findAll().stream()
                .map(EntityToDto::toDto).collect(Collectors.toList());
    }

    public CategoryDto getById(long id){
        return EntityToDto.toDto(categoryRepository.getOne(id));
    }

    public CategoryDto getByName(String name){
        return EntityToDto.toDto(categoryRepository.findByName(name));
    }
}
