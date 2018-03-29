package ru.reksoft.onlineShop.domain.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "order_info")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    private UserEntity userEntity;

    @ManyToOne
    private StatusEntity statusEntity;

    @Column(name = "date")
    private Date date;

    @Column(nullable = false)
    private String deliveryAddress;



//    @Column(name = "count", nullable = false)
//    @AttributeOverrides({@AttributeOverride(name = "count", column = )})
//    private int count;

    @ManyToMany()
    @JoinTable(name = "order_item",
            joinColumns ={@JoinColumn(name = "item_id")},//, @JoinColumn(name = "count", referencedColumnName = "count")},
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<ItemEntity> itemEntityList;


}
