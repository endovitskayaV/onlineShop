package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
}
