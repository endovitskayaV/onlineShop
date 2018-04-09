package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;

import java.util.Date;
import java.util.List;

/**
 * Data transfer object for OrderEntity
 * @see OrderEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
    private long id;
    private long userId;
    private long statusId;
    private Date date;
    private String deliveryAddress;
    private List<OrderedItemDto> items;
}
