package ru.reksoft.onlineShop.domain.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="statusEntity")
public class StatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;
}
