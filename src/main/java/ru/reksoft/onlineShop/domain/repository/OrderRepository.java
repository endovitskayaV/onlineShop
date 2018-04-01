package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.repository.CrudRepository;
import ru.reksoft.onlineShop.domain.entity.OrderEntity;

public interface OrderRepository extends CrudRepository<OrderEntity, Long> {
}
