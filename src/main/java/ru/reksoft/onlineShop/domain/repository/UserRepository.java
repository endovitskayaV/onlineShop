package ru.reksoft.onlineShop.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
