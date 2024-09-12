package com.test.qikserve.qikserve.domain.service.strategy;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.model.Promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FlatPercentStrategy implements PromotionStrategy {

    private BigDecimal discount;

    @Override
    public BigDecimal applyPromotion(CartProduct cartProduct, Promotion promotion) {
        var product = cartProduct.getProduct();
        BigDecimal productValue = product.getPrice().multiply(BigDecimal.valueOf(cartProduct.getQuantity()));

        discount = (productValue.multiply(promotion.getAmount()))
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);

        return productValue.subtract(discount);
    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }
}
