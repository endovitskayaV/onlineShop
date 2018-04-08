package ru.reksoft.onlineShop.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "category")
/*
* eg: food, smartphones, cloth, etc
* */
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rating", nullable = false)
    /*
    * Category popularity
    * */
    private int rating;

    @ElementCollection
    @CollectionTable(name = "category_characteristic", joinColumns = @JoinColumn(name = "category_id"))
    @MapKeyJoinColumn(name = "characteristic_id")
    @Column(name = "required")
    /*
     * Map contains characteristic and value
     * that shows if the characteristic is required for this category.
     * In db it is stored in additional table:
     * PK(characteristic_id, category_id), required
     * */
    private Map<CharacteristicEntity, Boolean> characteristicRequired;
}
