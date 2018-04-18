package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * eg: color, weight, etc
 */
@Entity
@Table(name = "characteristic")
@Data
@EqualsAndHashCode(of = "id")
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
    @Column(name = "measure_unit", nullable = false)
    private String measureUnit;

    @Column(name = "value_data_type", nullable = false)
    @Enumerated(value = EnumType.STRING)
    private DataType valueDataType;

}
