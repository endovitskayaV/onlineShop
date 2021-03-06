package ru.reksoft.onlineShop.model.domain.repository;

import org.hibernate.annotations.NamedQuery;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.dto.ItemDto;

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
    List<ItemEntity> findAllByCategoryIdOrderByPopularity(long categoryId);

    List<ItemEntity> findAllByCategoryId(long categoryId);

    List<ItemEntity> findAllByCategoryId(long categoryId, Sort sort);

    /**
     * Finds itemEntity by its name and producer name
     *
     * @param name     item name
     * @param producer producer name
     * @return itemEntity found by its name and producer
     */
    ItemEntity findByNameAndProducer(String name, String producer);

    List<ItemEntity> findAllByIdIn(long[] ids, Sort sort);

}
