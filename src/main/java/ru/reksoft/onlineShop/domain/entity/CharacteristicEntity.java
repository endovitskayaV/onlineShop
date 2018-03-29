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

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToMany()
    @JoinTable(name = "category_characteristic",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))
    private List<CategoryEntity> categoryList;

    @Transient
    private boolean required;


//    //!!!!!!!!!!!
//    @JoinTable(name = "characteristic_item")
//    private String value;

//    //!!!!!!!!!!!
//    @JoinTable(name = "category_characteristic",
//            joinColumns = @JoinColumn(name = "is_required"),
//            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))

    //@OneToOne(mappedBy = "category_characteristic")
   // @JoinColumn(name = "is_required")

//    @JoinTable(name = "category_characteristic",
//           joinColumns = @JoinColumn(name = "is_required"),
//            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))

//    @ManyToOne
//    @JoinTable(name = "category_characteristic",
//            joinColumns = @JoinColumn(name = "is_required"),
//            inverseJoinColumns = @JoinColumn(name = "characteristic_id"))
//    @Column()

}
