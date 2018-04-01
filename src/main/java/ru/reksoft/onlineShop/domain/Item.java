package ru.reksoft.onlineShop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Item {

    private long id;
    private String name;
   // private String producer;
    private int storage;
    private String description;
    private int price;
}
