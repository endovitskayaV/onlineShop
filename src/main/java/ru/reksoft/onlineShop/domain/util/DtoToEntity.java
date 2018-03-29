package ru.reksoft.onlineShop.domain.util;

import ru.reksoft.onlineShop.domain.dto.*;
import ru.reksoft.onlineShop.domain.entity.*;

import java.util.stream.Collectors;

public class DtoToEntity {
    public static ItemEntity toEntity(ItemDto itemDto) {
        if (itemDto == null) return null;
        return ItemEntity.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .storage(itemDto.getStorage())
                .price(itemDto.getPrice())
                .category(toEntity(itemDto.getCategory()))
                .characteristicList(
                        itemDto.getCharacteristicList().stream()
                                .map(DtoToEntity::toEntity).collect(Collectors.toList()))
                .build();
    }


    public static CharacteristicEntity toEntity(CharacteristicDto characteristicDto) {
        if (characteristicDto == null) return null;
        return CharacteristicEntity.builder()
                .id(characteristicDto.getId())
                .name(characteristicDto.getName())
                .type(characteristicDto.getType())
                .categoryList(characteristicDto.getCategoryList().stream()
                        .map(DtoToEntity::toEntity).collect(Collectors.toList()))
                .build();
    }

    public static CategoryEntity toEntity(CategoryDto categoryDto) {
        if (categoryDto == null) return null;
        return CategoryEntity.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .description(categoryDto.getDescription())
                .rating(categoryDto.getRating())
                .build();
    }

    public static UserEntity toEntity(UserDto userDto) {
        if (userDto == null) return null;
        return UserEntity.builder()
                .id(userDto.getId())
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .role(userDto.getRole())
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

    public static OrderEntity toEntity(OrderDto orderDto){
        if (orderDto!=null) return null;
        return OrderEntity.builder()
                .id(orderDto.getId())
                .date(orderDto.getDate())
                .deliveryAddress(orderDto.getDeliveryAddress())
                .itemList(orderDto.getItemList().stream()
                        .map(DtoToEntity::toEntity).collect(Collectors.toList()))
                .user(DtoToEntity.toEntity(orderDto.getUser()))
                .status(DtoToEntity.toEntity(orderDto.getStatus()))
                .build();
    }
}
