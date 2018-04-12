package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Map;

/**
 * Order that have status
 * can be a basket according to status
 *
 * @see StatusEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@ToString
@Table(name = "order_info")
public class OrderEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    /**
     * Whether it is sent or get by customer or is shifting , etc
     *
     * @see StatusEntity
     */
    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusEntity status;

    /**
     * Date of an order
     */
    @Column(name = "date")
    private Date date;

    @Column(nullable = false)
    private String deliveryAddress;

    /**
     * Map that contains item and its quantity, chosen by customer
     * In db it is stored in additional table:
     * PK(order_id, item_id), quantity
     *
     * @see ItemEntity
     */
    @ElementCollection
    @CollectionTable(name = "order_info_item", joinColumns = @JoinColumn(name = "order_id"))
    @MapKeyJoinColumn(name = "item_id")
    @Column(name = "quantity")
    private Map<ItemEntity, Integer> itemsQuantity;


}
