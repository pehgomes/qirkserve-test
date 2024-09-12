package com.test.qikserve.qikserve.domain.service.strategy;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.model.Promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class QtyBasePriceStrategy implements PromotionStrategy {

    private BigDecimal discount;

    @Override
    public BigDecimal applyPromotion(CartProduct cartProduct, Promotion promotion) {
        var product = cartProduct.getProduct();
        BigDecimal productValue = product.getPrice();

        var total = productValue.multiply(BigDecimal.valueOf(cartProduct.getQuantity()));

        if (cartProduct.getQuantity() >= promotion.getRequiredQuantity()) {
            discount = promotion.getPrice();
            total = total.subtract(promotion.getPrice());
        }

        return total;
    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }
}
