package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.CategoryEntity;
import ru.reksoft.onlineShop.domain.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
