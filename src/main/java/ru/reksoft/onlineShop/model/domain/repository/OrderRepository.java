package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;

import java.util.List;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findAllByStatusIdAndUserId(long statusId, long userId);
        OrderEntity findByStatusIdAndUserId(long statusId, long userId);

    List<OrderEntity> findAllByUserIdAndStatusIdGreaterThan(long userId, long statusId);
}
