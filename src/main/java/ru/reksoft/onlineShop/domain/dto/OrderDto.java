package ru.reksoft.onlineShop.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class OrderDto {

    private long id;
    private UserDto user;
    private StatusDto status;
    private Date date;
    private String deliveryAddress;
    private List<ItemDto> itemList;



//    @Column(name = "count", nullable = false)
//    @AttributeOverrides({@AttributeOverride(name = "count", column = )})
//    private int count;




}
