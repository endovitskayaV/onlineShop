package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.entity.OrderEntity;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findOrderEntityByUserIdAndStatusId(long userId, long statusId);
  //  OrderEntity findOrderEntitiesByUserIdAndStatusId(long userId, long statusId);
}
