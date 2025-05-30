package com.deere.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private int price;
    @Column(name = "discount_price")
    private int discountedPrice;
    @Column(name = "discount_percent")
    private int discountPercent;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "brand")
    private String brand;
    @Column(name = "color")
    private String color;
    @Embedded
    @ElementCollection
    @Column(name = "sizes")
    private Set<Size> sizes =new HashSet<>();
    @Column(name = "image_url")
    private String imageUrl;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Rating> ratings= new ArrayList<>();
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Review> reviews= new ArrayList<>();
    @Column(name = "num_ratings")
    private int numRatings;
    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;
    private LocalDateTime createdAt;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnore
    private List<CartItem> cartItems = new ArrayList<>();
}
