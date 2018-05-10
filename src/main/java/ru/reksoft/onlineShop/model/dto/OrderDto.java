package ru.reksoft.onlineShop.model.dto;

import lombok.*;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;
import ru.reksoft.onlineShop.validating.alphabeticNumericData.AlphabeticNumericData;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
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

    @NotBlank(message = "Address must contain at least one not blank character")
    @AlphabeticNumericData
    private String deliveryAddress;

    private List<OrderedItemDto> items;
}
