package ru.reksoft.onlineShop.domain.converter;

import ru.reksoft.onlineShop.domain.entity.*;
import ru.reksoft.onlineShop.domain.repository.CategoryRepository;
import ru.reksoft.onlineShop.domain.repository.ItemRepository;
import ru.reksoft.onlineShop.model.dto.*;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class DtoToEntity {
    public static CharacteristicEntity toEntity(CharacteristicDto characteristicDto) {
        if (characteristicDto == null) return null;
        return CharacteristicEntity.builder()
                .id(characteristicDto.getId())
                .name(characteristicDto.getName())
                .type(characteristicDto.getType())
                .build();
    }

    public static CategoryEntity toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .rating(categoryDto.getRating())
                .characteristicRequiredMap(categoryDto.getCharacteristicRequiredMap().entrySet()
                        .stream().collect((Collectors.toMap(x -> toEntity(x.getKey()),
                                Map.Entry::getValue))))
                .build();
    }

    public static RoleEntity toEntity(RoleDto roleDto) {
        if (roleDto == null) return null;
        return RoleEntity.builder()
                .id(roleDto.getId())
                .name(roleDto.getName())
                .description(roleDto.getDescription())
                .build();
    }

    public static UserEntity toEntity(UserDto userDto) {
        if (userDto == null) return null;
        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(toEntity(userDto.getRole()))
                .name(userDto.getName())
                .surname(userDto.getSurname())
                .parentalName(userDto.getParentalName())
                .address(userDto.getAddress())
                .phoneNumber(userDto.getPhoneNumber())
                .build();
    }

    public static StatusEntity toEntity(StatusDto statusDto) {
        if (statusDto == null) return null;
        return StatusEntity.builder()
                .id(statusDto.getId())
                .name(statusDto.getName())
                .description(statusDto.getDescription())
                .build();
    }

    public static OrderEntity toEntity(OrderDto orderDto) {
        if (orderDto == null) return null;
        return OrderEntity.builder()
                .id(orderDto.getId())
                .date(orderDto.getDate())
                .deliveryAddress(orderDto.getDeliveryAddress())
                .user(DtoToEntity.toEntity(orderDto.getUser()))
                .status(DtoToEntity.toEntity(orderDto.getStatus()))
//                .itemQuantityMap(orderDto.getItemQuantityMap().entrySet()
//                        .stream().collect(Collectors.toMap(x -> toEntity(x.getKey()),
//                                Map.Entry::getValue)))getValue
                .build();
    }
}
