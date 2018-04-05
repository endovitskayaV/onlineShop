package ru.reksoft.onlineShop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.model.dto.OrderDto;
import ru.reksoft.onlineShop.domain.repository.OrderRepository;
import ru.reksoft.onlineShop.domain.converter.EntityToDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

//    public OrderDto getOrderByUserIdAndStatus(long userId, long statusId){
//        return EntityToDto.toDto(orderRepository.findOrderEntityByUserIdAndStatusId(userId, statusId));
//    }

//    public OrderDto getBusket(long userId){
////        return EntityToDto.toDto(orderRepository.findOrderEntityByUserIdAndStatusId(userId, 0));
//    }

    //public void save(OrderDto orderDto){
    // orderRepository.save(DtoToEntity.toEntity(orderDto));
    // }

    public List<OrderDto> getAll() {
        return orderRepository.findAll().stream().map(EntityToDto::toDto).collect(Collectors.toList());
    }
}