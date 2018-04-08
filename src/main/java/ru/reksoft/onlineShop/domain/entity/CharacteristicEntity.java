package ru.reksoft.onlineShop.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "characteristic")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
/*
* eg: color, weight, etc
* */
public class CharacteristicEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    /*
    * Unit of characteristic`s measure
    * eg: metres, gramme, etc
    * */
    private String type;

}
