package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;

/**
 * Iteracts with database regarding characteristic
 */
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, Long> {
    CharacteristicEntity getByCode(String code);

}
