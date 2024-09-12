package com.test.qikserve.qikserve.domain.model;

import com.test.qikserve.qikserve.domain.service.strategy.PromotionStrategy;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "tb_cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToMany(
            mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private List<CartProduct> products = new ArrayList<>();

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "total_saving")
    private BigDecimal totalSaving;

    public Cart(List<CartProduct> products) {

        if (products != null)
            products.forEach(this::addCartProduct);

        this.calculatePrices();
    }

    public Cart() {}

    public void calculatePrices() {
        this.total = BigDecimal.ZERO;
        this.totalSaving = BigDecimal.ZERO;

        if (this.products != null) {
            for (CartProduct cp : this.products) {
                for (Promotion promo : cp.getProduct().getPromotions()) {

                    PromotionStrategy strategy = promo.getPromotionType().getPromotionStrategy();

                    this.total = total.add(strategy.applyPromotion(cp, promo));
                    this.totalSaving = totalSaving.add(strategy.getDiscount());
                }
            }
        }
    }

    public void addCartProduct(CartProduct cartProduct) {
        this.products.add(cartProduct);
        cartProduct.setCart(this);
    }

}
