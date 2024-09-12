package com.test.qikserve.qikserve.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "product_promotion",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "promotion_id")
    )
    private Set<Promotion> promotions;

    public Product(String externalId, String name, BigDecimal price, Set<Promotion> promotions) {
        this.externalId = externalId;
        this.name = name;
        this.price = price;
        this.promotions = promotions;
    }



}
