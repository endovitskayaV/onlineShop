package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.converter.CategoryConverter;
import ru.reksoft.onlineShop.model.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.model.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.model.dto.CategoryDto;
import ru.reksoft.onlineShop.model.dto.NewCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Interacts with repositories to extract required objects
 * converts extracted entities to dtos
 */
@Service
public class CategoryService {
    private CategoryRepository categoryRepository;
    private CategoryConverter categoryConverter;

    /**
     * @param categoryRepository repository for category
     * @param categoryConverter  repository for category
     */
    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryConverter categoryConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
    }

    /**
     * Gets all categories stored is database
     *
     * @return all category dtos
     */
    public List<CategoryDto> getAll() {
        return categoryRepository.findAll().stream()
                .map(categoryConverter::toDto).collect(Collectors.toList());
    }

    /**
     * @param id category id
     * @return category dto found by its id
     */
    public CategoryDto getById(long id) {
        CategoryEntity categoryEntity=categoryRepository.findById(id).orElse(null);
        return categoryConverter.toDto(categoryRepository.getOne(id));
    }

    /**
     * @param name category name
     * @return category dto found by its name
     */
    public CategoryDto getByName(String name) {
        return categoryConverter.toDto(categoryRepository.findByName(name));
    }

    /**
     * Inserts to database new category
     *
     * @param newCategoryDto category dto that should be inserted to database
     * @return id of created category
     */
    public long add(NewCategoryDto newCategoryDto) {
        if (categoryRepository.findByName(newCategoryDto.getName()) != null) {
            return -1;
        } else {
            CategoryEntity categoryEntity = categoryConverter.toEntity(newCategoryDto);
            categoryEntity.setId(categoryRepository.count() + 1); //generate id
            return categoryRepository.save(categoryEntity).getId();
        }

    }
}
