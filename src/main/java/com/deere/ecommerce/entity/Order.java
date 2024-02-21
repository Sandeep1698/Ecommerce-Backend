package com.deere.ecommerce.entity;

import com.deere.ecommerce.user.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "order_id")
    private String orderId;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "delivery_date")
    private LocalDateTime deliveryDate;

    @OneToOne
    private Address shippingAddress;

    @Embedded
    private PaymentDetails paymentDetails = new PaymentDetails();

    @Column(name = "total_price")
    private double totalPrice;
    @Column(name = "total_discounted_price")
    private Integer totalDiscountedPrice;
    @Column(name = "discount")
    private Integer discount;
    @Column(name = "order_status")
    private OrderStatus orderStatus;
    @Column(name = "total_item")
    private int totalItem;
    @Column(name = "created_at")
    private LocalDateTime createdAt;


}
