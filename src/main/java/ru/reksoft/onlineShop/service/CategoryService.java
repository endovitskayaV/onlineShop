package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.converter.CategoryConverter;
import ru.reksoft.onlineShop.domain.converter.DtoToEntity;
import ru.reksoft.onlineShop.domain.converter.EntityToDto;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.EditableCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter=categoryConverter;
    }

    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryConverter::toDto).collect(Collectors.toList());
    }

    public CategoryDto getById(long id) {
        return categoryConverter.toDto(categoryRepository.getOne(id));
    }

    public CategoryDto getByName(String name) {
        return categoryConverter.toDto(categoryRepository.findByName(name));
    }

    public long add(EditableCategoryDto editableCategoryDto) {
        CategoryEntity categoryEntity=categoryConverter.toEntity(editableCategoryDto);
        categoryEntity.setId(categoryRepository.count()+1);
        return categoryRepository.save(categoryEntity).getId();
    }
}
