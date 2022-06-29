package com.hervmo.manager.models.entities;

import com.hervmo.manager.utils.OrderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Table(name = "Orders")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order extends InfoUtil {
    @Id
    @SequenceGenerator(
            name = "order_id_sequence",
            sequenceName = "order_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "order_id_sequence"
    )
    private Long orderId;
    private String orderNumber;
    private double taxRate;
    private double netPrice;
    private double totalPrice;
    private String hashCart;
    @Enumerated(EnumType.STRING)
    private OrderType orderType;


    @ManyToOne(fetch = FetchType.EAGER)
    private Customer customer;

    @OneToMany(mappedBy = "order")
    private List<OrderDetails> orderDetailsList;


    @ManyToOne
    private States orderStatus;
}
