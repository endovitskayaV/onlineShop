package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.ItemEntity;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}
