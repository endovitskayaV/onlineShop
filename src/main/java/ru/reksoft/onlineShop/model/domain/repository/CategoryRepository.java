package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.CategoryEntity;

/**
 * Iteracts with database regarding category
 */
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {
    /**
     * Finds CategoryEntity by its name
     *
     * @param name categoryName
     * @return categoryEntity found by name
     */
    CategoryEntity findByName(String name);
}
