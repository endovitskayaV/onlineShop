package ru.reksoft.onlineShop.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.domain.entity.CharacteristicEntity;

public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Long> {

}
