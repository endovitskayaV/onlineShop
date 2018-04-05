package ru.reksoft.onlineShop.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "producer", nullable = false)
    private String producer;

    @Column(name = "storage", nullable = false)
    private int storage;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @NotNull
    private CategoryEntity category;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name ="characteristic_item",joinColumns = @JoinColumn(name = "item_id"))
    @MapKeyJoinColumn(name = "characteristic_id")
    @Column(name = "value")
    private Map<CharacteristicEntity, String> characteristicValueMap;

}
