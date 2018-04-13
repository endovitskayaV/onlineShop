package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.domain.converter.OrderConverter;
import ru.reksoft.onlineShop.model.domain.entity.ItemEntity;
import ru.reksoft.onlineShop.model.domain.entity.OrderEntity;
import ru.reksoft.onlineShop.model.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.domain.repository.OrderRepository;
import ru.reksoft.onlineShop.model.domain.repository.StatusRepository;
import ru.reksoft.onlineShop.model.domain.repository.UserRepository;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.model.dto.OrderedItemDto;

import java.util.Date;
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
    private ItemRepository itemRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository,
                        OrderConverter orderConverter,
                        StatusRepository statusRepository,
                        UserRepository userRepository,
                        ItemRepository itemRepository) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
    }

    public OrderDto getById(long id) {
        return orderConverter.toDto(orderRepository.findById(id).orElse(null));
    }

    public List<OrderDto> getAllOrderedAndUserId(long userId) {
        return orderRepository.findAllByUserIdAndStatusIdGreaterThan(userId, 1).stream()
                .map(orderConverter::toDto).collect(Collectors.toList());
    }

    public OrderDto getBasket(long userId) {
        List<OrderEntity> orders = orderRepository.findAllByStatusIdAndUserId(1, userId);
        return (orders.size() != 0) ? orderConverter.toDto(orders.get(0)) : null;
    }


    public void addToBasket(long userId, long itemId) {
        List<OrderEntity> orders = orderRepository.findAllByStatusIdAndUserId(1, userId);
        OrderEntity basket = (orders.size() != 0) ? orders.get(0) : null;
        if (basket == null) {
            //create new basket
            Map<ItemEntity, Integer> itemsQuantity = new HashMap<>(1);
            itemsQuantity.put(itemRepository.findById(itemId).get(), 1); //1 item by default
            orderRepository.save(OrderEntity.builder()
                    .id(orderRepository.count() + 1)
                    .status(statusRepository.findById(1L).get()) //1- in basket
                    .user(userRepository.findById(userId).get())
                    .itemsQuantity(itemsQuantity)
                    .build());
        } else {
            //add item to basket
            ItemEntity addableItem = itemRepository.findById(itemId).get();
            Map<ItemEntity, Integer> itemsQuantity = basket.getItemsQuantity();
            if (itemsQuantity.keySet().stream()
                    .anyMatch(itemEntity -> itemEntity.equals(addableItem))) {
                itemsQuantity.put(addableItem, itemsQuantity.get(addableItem) + 1);
            } else {
                itemsQuantity.put(itemRepository.findById(itemId).get(), 1);
            }
            basket.setItemsQuantity(itemsQuantity);
            orderRepository.save(basket);
        }
    }

    public int increaseItemQuantity(long basketId, OrderedItemDto orderedItemDto) {
        OrderEntity basket = orderRepository.findById(basketId).get();
        Map<ItemEntity, Integer> itemsQuantity = basket.getItemsQuantity();
        ItemEntity itemEntity = itemRepository.findById(orderedItemDto.getItemId()).get();
        if (itemEntity.getStorage() < orderedItemDto.getQuantity()) {
            return itemEntity.getStorage(); //cannot increase item quantity
        } else {
            itemsQuantity.put(itemEntity, orderedItemDto.getQuantity());
            basket.setItemsQuantity(itemsQuantity);
            orderRepository.save(basket);
            return 1;
        }
    }

    public boolean deleteItem(long basketId, long itemId) {
        OrderEntity basket = orderRepository.findById(basketId).get();
        Map<ItemEntity, Integer> itemsQuantity = basket.getItemsQuantity();
        ItemEntity foundItemEntity = itemRepository.findById(itemId).get();

        if (itemsQuantity.keySet().stream()
                .noneMatch(itemEntity -> itemEntity.equals(foundItemEntity))) {
            return false;
        } else {

            itemsQuantity.remove(foundItemEntity);

            if (itemsQuantity.size() == 0) { //whether to delete whole basket
                orderRepository.delete(basket);
            } else {
                basket.setItemsQuantity(itemsQuantity);
                orderRepository.save(basket);
            }
            return true;
        }
    }


    public long basketToOrder(long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).get();
        orderEntity.setStatus(statusRepository.findById(1L).get()); //1- unfinished order
        return orderRepository.save(orderEntity).getId();
    }

    public void finishOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.findById(orderDto.getId()).get();
        orderEntity.setStatus(statusRepository.findById(2L).get()); //2- finished order
        orderEntity.setDate(new Date());
        orderEntity.setDeliveryAddress(orderDto.getDeliveryAddress());
        orderRepository.save(orderEntity);
    }
}

