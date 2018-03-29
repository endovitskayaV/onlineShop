package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
