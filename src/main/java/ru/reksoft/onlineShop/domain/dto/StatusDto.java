package ru.reksoft.onlineShop.domain.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Builder
public class StatusDto {
    private long id;
    private String name;
    private String description;
}
