package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    OrderEntity findByStatusIdAndUserId(long statusId, long userId);

    List<OrderEntity> findAllByUserId(long userId);
}
