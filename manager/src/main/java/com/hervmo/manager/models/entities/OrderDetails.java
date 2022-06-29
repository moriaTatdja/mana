package com.hervmo.manager.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetails {
    @Id
    @SequenceGenerator(
            name = "order_detail_sequence",
            sequenceName = "order_detail_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_detail_sequence"
    )
    private Long orderDetailId;
    private int quantity;
    private double price;
    private double taxRate;

    @ManyToOne(fetch = FetchType.EAGER)
    private Articles article;

    @ManyToOne(cascade = CascadeType.ALL)
    private Order order;


}
