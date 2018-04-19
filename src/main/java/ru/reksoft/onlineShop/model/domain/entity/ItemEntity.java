package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Map;

/**
 * Item that is bought by customers and sold by sellers
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item",
        uniqueConstraints = {@UniqueConstraint(name = "unique_item", columnNames = {"name", "producer"})})
public class ItemEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    
    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "producer", nullable = false)
    private String producer;

    /**
     * Quantity of items in stock
     */

    @Column(name = "storage", nullable = false)
    private int storage;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    /**
     * item category
     * eg: smartphone, food, health, etc
     *
     * @see CategoryEntity
     */

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity category;


    /**
     * Map that contains characteristic and its value
     * In db it is stored in additional table:
     * PK(characteristic_id, item_id), value
     *
     * @see CharacteristicEntity
     */
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "characteristic_item", joinColumns = @JoinColumn(name = "item_id"))
    @MapKeyJoinColumn(name = "characteristic_id")
    @Column(name = "value")
    private Map<CharacteristicEntity, String> characteristicsValues;


    @Column(name = "photo_path")
    private String photoPath;


}
