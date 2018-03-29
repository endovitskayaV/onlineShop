package ru.reksoft.onlineShop.domain.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDto {

    private long id;
    private UserDto userDto;
    private StatusDto statusDto;
    private Date date;
    private String deliveryAddress;
    private List<ItemDto> itemDtoList;



//    @Column(name = "count", nullable = false)
//    @AttributeOverrides({@AttributeOverride(name = "count", column = )})
//    private int count;




}
