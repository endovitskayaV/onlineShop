package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.converter.CategoryConverter;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
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

    public long add(NewCategoryDto newCategoryDto) {
        return 1;
//        if (categoryRepository.findByName(newCategoryDto.getName()) != null) {
//            return -1;
//        } else {
//            CategoryEntity categoryEntity = categoryConverter.toEntity(newCategoryDto);
//            long c=categoryRepository.count();
//            categoryEntity.setId(10);
//            CategoryEntity categoryEntity1=categoryRepository.save(categoryEntity);
//            return categoryEntity1.getId();
//        }

    }
}
