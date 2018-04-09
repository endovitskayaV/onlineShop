package ru.reksoft.onlineShop.model.dto;


import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;

/**
 * Data transfer object for ItemEntity that was ordered
 * contatins quantity of ordered item
 * @see ItemEntity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderedItemDto {
    private long itemId;
    private int quantity;
}
