package com.test.qikserve.qikserve.domain.model;

import com.test.qikserve.qikserve.domain.types.PromotionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(name = "external_id", unique = true)
    private String externalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "promotion_type")
    private PromotionType promotionType;

    @Column(name = "required_quantity")
    private Long requiredQuantity;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "free_quantity")
    private Long freeQuantity;

    @ManyToMany(mappedBy = "promotions", cascade = CascadeType.ALL)
    private List<Product> products;

    public Promotion(String externalId, PromotionType promotionType, Long requiredQuantity, BigDecimal price,
                     BigDecimal amount, Long freeQuantity) {
        this.externalId = externalId;
        this.promotionType = promotionType;
        this.requiredQuantity = requiredQuantity;
        this.price = price;
        this.amount = amount;
        this.freeQuantity = freeQuantity;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Promotion promotion = (Promotion) object;
        return Objects.equals(externalId, promotion.externalId)
                && promotionType == promotion.promotionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(externalId, promotionType, requiredQuantity, price, amount, freeQuantity);
    }
}
