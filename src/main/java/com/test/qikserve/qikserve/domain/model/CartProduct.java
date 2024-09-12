package com.test.qikserve.qikserve.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "tb_cart_product")
public class CartProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @JoinColumn(name = "product_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Setter
    @Column(name = "quantity")
    private Long quantity;

    @Column
    private BigDecimal amount;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    private Cart cart;

    public CartProduct(Product product, Long quantity, Cart cart) {
        this.product = product;
        this.quantity = quantity;
        this.calculateGrossAmount();
        this.cart = cart;
    }

    public void removeProductItem() {
        if (this.quantity > 1) {
            this.quantity = this.quantity - 1;
            this.calculateGrossAmount();
        }
    }

    public void calculateGrossAmount() {
        this.amount = product.getPrice().multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    public boolean isUniqueProduct() {
        return this.quantity != null && this.quantity == 1;
    }

    public CartProduct(Product product, Long quantity) {
        this.product = product;
        this.quantity = quantity;
        this.calculateGrossAmount();
    }

    public void incrementQuantity(Long quantity) {
        this.quantity = this.quantity + quantity;
    }
}
