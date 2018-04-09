package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.converter.ItemConverter;
import ru.reksoft.onlineShop.model.domain.converter.OrderConverter;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;
import ru.reksoft.onlineShop.model.domain.repository.OrderRepository;
import ru.reksoft.onlineShop.model.domain.repository.StatusRepository;
import ru.reksoft.onlineShop.model.domain.repository.UserRepository;
import ru.reksoft.onlineShop.model.dto.ItemDto;
import ru.reksoft.onlineShop.model.dto.OrderDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;
    private OrderConverter orderConverter;
    private StatusRepository statusRepository;
    private UserRepository userRepository;
    private ItemConverter itemConverter;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderConverter orderConverter,
                        StatusRepository statusRepository,
                        UserRepository userRepository,
                        ItemConverter itemConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.itemConverter = itemConverter;
    }

    public OrderDto getById(long id) {
        return orderConverter.toDto(orderRepository.findById(id).orElse(null));
    }

    public OrderDto getByStatusIdAndUserId(long statusId, long userId) {
        return orderConverter.toDto(orderRepository.findByStatusIdAndUserId(statusId, userId));
    }

    public List<OrderDto> getAllUserId(long userId) {
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderConverter::toDto).collect(Collectors.toList());
    }

    public void createBasket(long userId, ItemDto itemDto) {
        Map<ItemEntity, Integer> itemsQuantity = new HashMap<>(1);
        itemsQuantity.put(itemConverter.toEntity(itemDto), 1);
        orderRepository.save(OrderEntity.builder()
                .id(orderRepository.count() + 1)
                .status(statusRepository.findById(1L).get())
                .user(userRepository.findById(userId).get())
                .itemsQuantity(itemsQuantity)
                .build());
    }
}

