package ru.reksoft.onlineShop.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "order_info")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    @NotNull
    /*
     * Whether it is send or get by customer, etc
     * */
    private StatusEntity status;

    @Column(name = "date")
    /*
     * Date of an order
     * */
    private Date date;

    @Column(nullable = false)
    private String deliveryAddress;

    @ElementCollection
    @CollectionTable(name = "order_info_item", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "item_id")
    @Column(name = "quantity")
    /*
     * Map that contains item and its quantity, chosen by customer
     * In db it is stored in additional table:
     * PK(order_id, item_id), quantity
     * */
    private Map<ItemEntity, Integer> itemQuantity;


}
