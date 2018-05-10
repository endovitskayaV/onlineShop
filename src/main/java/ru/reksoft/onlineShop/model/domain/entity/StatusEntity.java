package ru.reksoft.onlineShop.model.domain.entity;

import lombok.*;

import javax.persistence.*;

/**
 * Whether it is sent or get by customer or is shifting , etc
 */
@Data
@EqualsAndHashCode(of = "id")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name="status")
public class StatusEntity {
    @Id
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="description")
    private String description;
}
