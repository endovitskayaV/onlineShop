package ru.reksoft.onlineShop.domain.dto;

import lombok.Data;

import javax.persistence.*;

@Data
public class StatusDto {
    private long id;
    private String name;
    private String description;
}
