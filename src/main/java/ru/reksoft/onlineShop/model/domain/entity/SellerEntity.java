package ru.reksoft.onlineShop.model.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@EqualsAndHashCode(of = "userId")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "seller")
public class SellerEntity implements Serializable {
    @Id
    @Column(name = "user_id", nullable = false, unique = true)
    private long userId;

    @Column(name = "income", nullable = false)
    private long income;
}
