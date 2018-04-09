package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

/**
 * eg: food, smartphones, cloth, etc
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "category")
public class CategoryEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    /**
     * Category popularity
     */
    @Column(name = "rating", nullable = false)
    private int rating;

    /**
     * Map contains characteristic and value
     * that shows if the characteristic is required for this category.
     *
     * @see CharacteristicEntity
     * In db it is stored in additional table:
     * PK(characteristic_id, category_id), required
     */
    @ElementCollection
    @CollectionTable(name = "category_characteristic", joinColumns = @JoinColumn(name = "category_id"))
    @MapKeyJoinColumn(name = "characteristic_id")
    @Column(name = "required")
    private Map<CharacteristicEntity, Boolean> characteristicsRequired;
}
