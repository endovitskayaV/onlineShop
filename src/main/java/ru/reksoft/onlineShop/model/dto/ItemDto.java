package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;

import java.util.List;


/**
 * Data transfer object for ItemEntity
 *
 * @see ItemEntity
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ItemDto {
    private long id;

    private String name;

    private String producer;

    /**
     * Quantity of items in stock
     */
    private Integer storage;

    private String description;

    private Integer price;

    /**
     * id of item category
     * eg: smartphone, food, health, etc
     *
     * @see CategoryDto
     */
    private long categoryId;

    /**
     * item characteristics
     * eg: color, weight, etc
     */
    private List<CharacteristicDto> characteristics;

    /**
     * path in file system to item photo
     */
    private String photoName;

}
