package ru.reksoft.onlineShop.domain.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "characteristic")
@Data
@Builder
public class CharacteristicEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="type", nullable = false)
    private String type;

    @ManyToMany()
    @JoinTable(name = "category_characteristic",
            joinColumns =@JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))
    private List<CategoryEntity> categoryEntityList;


//    //!!!!!!!!!!!
//    @JoinTable(name = "characteristic_item")
//    private String value;

//    //!!!!!!!!!!!
//    @JoinTable(name = "category_characteristic",
//    joinColumns = @JoinColumn(name = "is_required"))
//    private boolean isRequired;
}
