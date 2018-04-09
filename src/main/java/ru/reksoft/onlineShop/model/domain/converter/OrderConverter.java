package ru.reksoft.onlineShop.model.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;
import ru.reksoft.onlineShop.model.domain.repository.OrderRepository;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.OrderedItemDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderConverter {
    private OrderRepository orderRepository;
    private ItemConverter itemConverter;

    @Autowired
    public OrderConverter(OrderRepository orderRepository, ItemConverter itemConverter) {
        this.orderRepository = orderRepository;
        this.itemConverter = itemConverter;
    }

    public OrderEntity toEntity(OrderDto orderDto) {
        return orderDto == null ? null : orderRepository.findById(orderDto.getId()).orElse(null);
    }

    public OrderDto toDto(OrderEntity orderEntity) {
        if (orderEntity == null) {
            return null;
        } else {
            List<OrderedItemDto> orderedItemDtos = new ArrayList<>();
            orderEntity.getItemsQuantity()
                    .forEach((itemEntity, quantity) ->
                            orderedItemDtos.add(new OrderedItemDto(itemEntity.getId(), quantity)));

            return OrderDto.builder()
                    .id(orderEntity.getId())
                    .userId(orderEntity.getUser().getId())
                    .statusId(orderEntity.getStatus().getId())
                    .date(orderEntity.getDate())
                    .deliveryAddress(orderEntity.getDeliveryAddress())
                    .items(orderedItemDtos)
                    .build();
        }
    }
}
