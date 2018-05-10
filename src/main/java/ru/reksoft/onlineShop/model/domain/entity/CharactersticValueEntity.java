package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;

import javax.persistence.*;

@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "characteristic_value")
public class CharactersticValueEntity {

    @Id
    @Column(name = "id", nullable = false)
    long id;

    @Column(name = "value", nullable = false)
    String value;

    @ManyToOne
    @JoinColumn(name = "characteristic_id")
    CharacteristicEntity characteristic;
}
