package ru.reksoft.onlineShop.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.reksoft.onlineShop.model.domain.entity.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, Long> {

}
