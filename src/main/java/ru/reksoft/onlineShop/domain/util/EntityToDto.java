package ru.reksoft.onlineShop.domain.util;

import ru.reksoft.onlineShop.domain.dto.*;
import ru.reksoft.onlineShop.domain.entity.*;

import java.util.stream.Collectors;

public class EntityToDto {
    public static ItemDto toDto(ItemEntity itemEntity) {
        if (itemEntity == null) return null;
        return ItemDto.builder()
                .id(itemEntity.getId())
                .name(itemEntity.getName())
                .description(itemEntity.getDescription())
                .storage(itemEntity.getStorage())
                .price(itemEntity.getPrice())
                .category(toDto(itemEntity.getCategory()))
                .characteristicList(
                        itemEntity.getCharacteristicList()
                        .stream()
                        .map(EntityToDto::toDto).collect(Collectors.toList()))
                .build();
    }


    public static CharacteristicDto toDto(CharacteristicEntity characteristicEntity) {
        if (characteristicEntity == null) return null;
        return CharacteristicDto.builder()
                .id(characteristicEntity.getId())
                .name(characteristicEntity.getName())
                .type(characteristicEntity.getType())
                .categoryList(characteristicEntity.getCategoryList().stream()
                        .map(EntityToDto::toDto).collect(Collectors.toList()))
                .build();
    }

    public static CategoryDto toDto(CategoryEntity categoryEntity) {
        if (categoryEntity == null) return null;
        return CategoryDto.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .rating(categoryEntity.getRating())
                .build();
    }

    public static UserDto toDto(UserEntity userEntity) {
        if (userEntity == null) return null;
        return UserDto.builder()
                .id(userEntity.getId())
                .email(userEntity.getEmail())
                .password(userEntity.getPassword())
                .role(userEntity.getRole())
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

    public static OrderDto toDto(OrderEntity orderEntity){
        if (orderEntity!=null) return null;
        return OrderDto.builder()
                .id(orderEntity.getId())
                .date(orderEntity.getDate())
                .deliveryAddress(orderEntity.getDeliveryAddress())
                .itemList(orderEntity.getItemList().stream()
                        .map(EntityToDto::toDto).collect(Collectors.toList()))
                .user(EntityToDto.toDto(orderEntity.getUser()))
                .status(EntityToDto.toDto(orderEntity.getStatus()))
                .build();
    }
}
