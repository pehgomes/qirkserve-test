package com.test.qikserve.qikserve.domain.service.strategy;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.model.Promotion;

import java.math.BigDecimal;

@FunctionalInterface
public interface PromotionStrategy {

    BigDecimal applyPromotion(CartProduct cartProduct, Promotion promotion);

    default BigDecimal getDiscount() {
        return BigDecimal.ZERO;
    }
}
