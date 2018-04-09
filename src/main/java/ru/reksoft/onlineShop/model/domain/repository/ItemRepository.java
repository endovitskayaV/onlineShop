package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;

import java.util.List;

/**
 * Iteracts with database regarding item
 */
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    /**
     * Finds itemEntities by category id
     *
     * @param categoryId id of category
     * @return itemEntities found by categoryId
     */
    List<ItemEntity> findAllByCategoryId(long categoryId);

    /**
     * Finds itemEntity by its name and producer name
     *
     * @param name     item name
     * @param producer producer name
     * @return itemEntity found by its name and producer
     */
    ItemEntity findByNameAndProducer(String name, String producer);
}