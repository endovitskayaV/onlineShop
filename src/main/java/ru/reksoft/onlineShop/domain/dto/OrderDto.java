package ru.reksoft.onlineShop.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {

    private long id;
    private UserDto user;
    private StatusDto status;
    private Date date;
    private String deliveryAddress;
    private Map<ItemDto, Integer> itemQuantityMap;

//    @Column(name = "count", nullable = false)
//    @AttributeOverrides({@AttributeOverride(name = "count", column = )})
//    private int count;




}
