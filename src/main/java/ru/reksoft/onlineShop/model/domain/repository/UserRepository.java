package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
}
