package ru.reksoft.onlineShop.model.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.StatusEntity;

public interface StatusRepository extends JpaRepository<StatusEntity, Long> {
}
