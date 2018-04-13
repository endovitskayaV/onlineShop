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
    private static final long STATUS_ID_BASKET = 1;
    private static final long STATUS_ID_ORDER = 1;
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

    public List<OrderDto> getAllOrderedByUserId(long userId) {
        return orderRepository.findAllByUserIdAndStatusIdGreaterThan(userId, 1)
                .stream()  //1- in basket
                .map(orderConverter::toDto)
                .collect(Collectors.toList());
    }

    public OrderDto getBasket(long userId) {
        List<OrderEntity> orders = orderRepository.findAllByStatusIdAndUserId(STATUS_ID_BASKET, userId);
        return (orders.size() != 0) ? orderConverter.toDto(orders.get(0)) : null;
    }

    public boolean addToBasket(long userId, long itemId) {

        ItemEntity foundItemEntity = itemRepository.getOne(itemId);
        if (foundItemEntity.getStorage() == 0) {
            return false;
        }
        List<OrderEntity> orders = orderRepository.findAllByStatusIdAndUserId(STATUS_ID_BASKET, userId);
        OrderEntity basket = (orders.size() != 0) ? orders.get(0) : null;
        if (basket == null) {
            //create new basket
            Map<ItemEntity, Integer> itemsQuantity = new HashMap<>(1);
            itemsQuantity.put(itemRepository.getOne(itemId), 1); //1 item by default
            orderRepository.save(OrderEntity.builder()
                    .id(orderRepository.count() + 1)
                    .status(statusRepository.getOne(STATUS_ID_BASKET))
                    .user(userRepository.getOne(userId))
                    .itemsQuantity(itemsQuantity)
                    .build());
        } else {
            //add item to basket
            ItemEntity addableItem = itemRepository.getOne(itemId);
            Map<ItemEntity, Integer> itemsQuantity = basket.getItemsQuantity();
            boolean equal = itemsQuantity
                    .keySet()
                    .stream()
                    .anyMatch(addableItem::equals);
            if (equal) {
                itemsQuantity.put(addableItem, itemsQuantity.get(addableItem));
            } else {
                itemsQuantity.put(itemRepository.getOne(itemId), 1);
            }
            basket.setItemsQuantity(itemsQuantity);
            orderRepository.save(basket);
        }
        return true;
    }

    public int setItemQuantityInBasket(long basketId, OrderedItemDto orderedItemDto, boolean isIncrease) {
        OrderEntity basket = orderRepository.getOne(basketId);
        Map<ItemEntity, Integer> itemsQuantity = basket.getItemsQuantity();
        ItemEntity itemEntity = itemRepository.getOne(orderedItemDto.getItemId());

        if ((isIncrease && (itemEntity.getStorage() < orderedItemDto.getQuantity() + 1))
                || (!isIncrease && (orderedItemDto.getQuantity() - 1 < 0))) {
            return orderedItemDto.getQuantity(); //cannot increase item quantity
        } else {
            orderedItemDto.setQuantity(doOperation(isIncrease, orderedItemDto.getQuantity()));
            itemsQuantity.put(itemEntity, orderedItemDto.getQuantity());
            basket.setItemsQuantity(itemsQuantity);
            orderRepository.save(basket);
            return -1;
        }
    }

    private int doOperation(boolean increase, int number) {
        return increase ? ++number : --number;
    }

    public boolean deleteItem(long basketId, long itemId) {
        OrderEntity basket = orderRepository.getOne(basketId);
        Map<ItemEntity, Integer> itemsQuantity = basket.getItemsQuantity();
        ItemEntity foundItemEntity = itemRepository.getOne(itemId);

        if (itemsQuantity.keySet().stream()
                .noneMatch(itemEntity -> itemEntity.equals(foundItemEntity))) {
            return false;
        } else {
            //increase item quantity in stock
            ItemEntity itemEntity = itemRepository.getOne(itemId);
            itemEntity.setStorage(itemEntity.getStorage() + basket.getItemsQuantity().get(foundItemEntity));
            itemRepository.save(itemEntity);

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


    public boolean checkOrderDetails(OrderDto orderDto){
        //if (orderDto.get)
    }
    public void finishOrder(OrderDto orderDto) {
        OrderEntity orderEntity = orderRepository.getOne(orderDto.getId());
        orderEntity.setStatus(statusRepository.getOne(STATUS_ID_ORDER));
        orderEntity.setDate(new Date());
        orderEntity.setDeliveryAddress(orderDto.getDeliveryAddress());
        orderRepository.save(orderEntity);
    }
}

