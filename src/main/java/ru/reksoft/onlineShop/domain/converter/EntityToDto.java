package ru.reksoft.onlineShop.domain.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.reksoft.onlineShop.domain.entity.*;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.dto.*;

import java.util.Map;
import java.util.stream.Collectors;

;


public class EntityToDto {


    public static CharacteristicDto toDto(CharacteristicEntity characteristicEntity) {
        if (characteristicEntity == null) return null;

            return CharacteristicDto.builder()
                    .id(characteristicEntity.getId())
                    .name(characteristicEntity.getName())
                    .type(characteristicEntity.getType())
                    .build();

    }

    public static RoleDto toDto(RoleEntity roleEntity) {
        if (roleEntity == null) return null;
        return RoleDto.builder()
                .id(roleEntity.getId())
                .name(roleEntity.getName())
                .description(roleEntity.getDescription())
                .build();
    }

    public static UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) return null;
        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(toDto(userEntity.getRole()))
                .name(userEntity.getName())
                .surname(userEntity.getSurname())
                .parentalName(userEntity.getParentalName())
                .address(userEntity.getAddress())
                .phoneNumber(userEntity.getPhoneNumber())
                .build();
    }

    public static StatusDto toDto(StatusEntity statusEntity) {
        if (statusEntity == null) return null;
        return StatusDto.builder()
                .id(statusEntity.getId())
                .name(statusEntity.getName())
                .description(statusEntity.getDescription())
                .build();
    }

    public static OrderDto toDto(OrderEntity orderEntity) {
        if (orderEntity == null) return null;
        return OrderDto.builder()
                .id(orderEntity.getId())
                .date(orderEntity.getDate())
                .deliveryAddress(orderEntity.getDeliveryAddress())
                .user(EntityToDto.toDto(orderEntity.getUser()))
                .status(EntityToDto.toDto(orderEntity.getStatus()))
//                .itemQuantityMap(orderEntity.getItemQuantityMap().entrySet()
//                        .stream().collect(Collectors.toMap(x -> toDto(x.getKey()),
//                                Map.Entry::getValue)))
                .build();
    }
}
