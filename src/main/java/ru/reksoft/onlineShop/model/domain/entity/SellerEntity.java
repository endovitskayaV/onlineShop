package ru.reksoft.onlineShop.model.domain.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
//@EqualsAndHashCode(of = "user")
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "seller")
public class SellerEntity  implements Serializable{
    @Id
    @Column(name = "user_id")
    private long id;

    @Column(name = "income",nullable = false)
    private long income;
}
