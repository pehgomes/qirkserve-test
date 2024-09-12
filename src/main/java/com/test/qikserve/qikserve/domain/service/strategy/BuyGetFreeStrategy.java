package com.test.qikserve.qikserve.domain.service.strategy;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.model.Promotion;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BuyGetFreeStrategy implements PromotionStrategy {

    private BigDecimal discount;
    @Override
    public BigDecimal applyPromotion(CartProduct cartProduct, Promotion promotion) {

        if (cartProduct.getQuantity() >= promotion.getRequiredQuantity()) {
            int freeProducts = (cartProduct.getQuantity().intValue() / (promotion.getRequiredQuantity().intValue()
                    + promotion.getFreeQuantity().intValue())) * promotion.getFreeQuantity().intValue();

            int paidProducts = cartProduct.getQuantity().intValue() - freeProducts;

            discount = cartProduct.getProduct().getPrice().multiply(new BigDecimal(freeProducts))
                    .setScale(2, RoundingMode.HALF_EVEN);

            return cartProduct.getProduct().getPrice().multiply(new BigDecimal(paidProducts))
                    .setScale(2, RoundingMode.HALF_EVEN);
        }

        return cartProduct.getProduct().getPrice().multiply(new BigDecimal(cartProduct.getQuantity()))
                .setScale(2, RoundingMode.HALF_EVEN);
    }

    @Override
    public BigDecimal getDiscount() {
        return discount;
    }
}
