package ru.reksoft.onlineShop.model.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * eg: color, weight, etc
 */
@Entity
@Table(name = "characteristic")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CharacteristicEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Unit of characteristic`s measure
     * eg: metres, gramme, etc
     */
    @Column(name = "type", nullable = false)
    private String type;

}
