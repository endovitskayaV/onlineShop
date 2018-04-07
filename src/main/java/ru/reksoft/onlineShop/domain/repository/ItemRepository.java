package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;

import java.util.List;
import java.util.Map;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByCategoryId(long categoryId);
    ItemEntity findByNameAndProducer(String name, String producer);
}
