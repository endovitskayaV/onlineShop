package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.CharacteristicEntity;
import ru.reksoft.onlineShop.model.domain.entity.CharactersticValueEntity;

public interface CharactersticValueRepository extends JpaRepository<CharactersticValueEntity, Long> {
    CharactersticValueEntity findByCharacteristicIdAndValue(long characteristicId, String value);
}
