package ru.reksoft.onlineShop.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "item")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;


    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "storage", nullable = false)
    private int storage;

    @Column(name = "price", nullable = false)
    private int price;

    @ManyToOne()
    @JoinColumn(name = "category_id")
    @NotNull
    private CategoryEntity categoryEntity;


    @ManyToMany()
    @JoinTable(name = "characteristic_item",
            joinColumns =@JoinColumn(name = "characteristic_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<CharacteristicEntity> characteristicEntityList;
}
