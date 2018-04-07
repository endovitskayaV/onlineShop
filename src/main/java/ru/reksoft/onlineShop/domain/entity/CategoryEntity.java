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
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "rating", nullable = false)
    private int rating;


    @ElementCollection
    @CollectionTable(name ="category_characteristic", joinColumns = @JoinColumn(name = "category_id"))
    @MapKeyJoinColumn(name = "characteristic_id")
    @Column(name = "required")
    private Map<CharacteristicEntity, Boolean> characteristicRequiredMap;
}
