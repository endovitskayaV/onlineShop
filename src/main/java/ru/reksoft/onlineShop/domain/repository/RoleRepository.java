package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.RoleEntity;


public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
}
