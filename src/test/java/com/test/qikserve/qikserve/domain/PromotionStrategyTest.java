package com.test.qikserve.qikserve.domain;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.model.Promotion;
import com.test.qikserve.qikserve.domain.service.strategy.BuyGetFreeStrategy;
import com.test.qikserve.qikserve.domain.service.strategy.FlatPercentStrategy;
import com.test.qikserve.qikserve.domain.service.strategy.QtyBasePriceStrategy;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PromotionStrategyTest {

    @Test
    void shouldApplyPromotionBasePriceNotApplied() {
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(10.00));

        CartProduct cartProduct = mock(CartProduct.class);
        when(cartProduct.getProduct()).thenReturn(product);
        when(cartProduct.getQuantity()).thenReturn(1L);

        Promotion promotion = mock(Promotion.class);
        when(promotion.getRequiredQuantity()).thenReturn(2L);
        when(promotion.getPrice()).thenReturn(BigDecimal.valueOf(5.00));

        BigDecimal result = new QtyBasePriceStrategy().applyPromotion(cartProduct, promotion);

        assertEquals(BigDecimal.valueOf(10.00), result);
    }

    @Test
    void shouldTestApplyPromotionAndPromotionApplied() {
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(10.00));

        CartProduct cartProduct = mock(CartProduct.class);
        when(cartProduct.getProduct()).thenReturn(product);
        when(cartProduct.getQuantity()).thenReturn(3L);

        Promotion promotion = mock(Promotion.class);
        when(promotion.getRequiredQuantity()).thenReturn(2L);
        when(promotion.getPrice()).thenReturn(BigDecimal.valueOf(5.00));

        BigDecimal result = new QtyBasePriceStrategy().applyPromotion(cartProduct, promotion);

        BigDecimal expectedTotal = BigDecimal.valueOf(30.00).subtract(BigDecimal.valueOf(5.00));
        assertEquals(expectedTotal, result);
    }

    @Test
    void shouldTestApplyPromotionWithPercentageDiscount() {
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(100.00));

        CartProduct cartProduct = mock(CartProduct.class);
        when(cartProduct.getProduct()).thenReturn(product);
        when(cartProduct.getQuantity()).thenReturn(2L);

        Promotion promotion = mock(Promotion.class);
        when(promotion.getAmount()).thenReturn(BigDecimal.valueOf(10));

        BigDecimal result = new FlatPercentStrategy().applyPromotion(cartProduct, promotion);
        BigDecimal productValue = BigDecimal.valueOf(200.00);
        BigDecimal discount = productValue.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
        BigDecimal expectedTotal = productValue.subtract(discount);
        assertEquals(expectedTotal, result);
    }

    @Test
    void shouldTestApplyPromotionNoDiscount() {
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(50.00));

        CartProduct cartProduct = mock(CartProduct.class);
        when(cartProduct.getProduct()).thenReturn(product);
        when(cartProduct.getQuantity()).thenReturn(4L);

        Promotion promotion = mock(Promotion.class);
        when(promotion.getAmount()).thenReturn(BigDecimal.ZERO);

        BigDecimal result = new FlatPercentStrategy().applyPromotion(cartProduct, promotion);

        BigDecimal productValue = BigDecimal.valueOf(200.00).setScale(2, RoundingMode.HALF_EVEN);
        assertEquals(productValue, result);
    }

    @Test
    void shoulfTestApplyPromotionWithFreeProducts() {
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(50.00));

        CartProduct cartProduct = mock(CartProduct.class);
        when(cartProduct.getProduct()).thenReturn(product);
        when(cartProduct.getQuantity()).thenReturn(10L);

        Promotion promotion = mock(Promotion.class);
        when(promotion.getRequiredQuantity()).thenReturn(3L);
        when(promotion.getFreeQuantity()).thenReturn(1L);

        BigDecimal result = new BuyGetFreeStrategy().applyPromotion(cartProduct, promotion);

        int paidProducts = 8;
        BigDecimal expectedTotal = BigDecimal.valueOf(50.00).multiply(BigDecimal.valueOf(paidProducts))
                .setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedTotal, result);
    }

    @Test
    void shouldTestApplyPromotionWithoutFreeProducts() {
        Product product = mock(Product.class);
        when(product.getPrice()).thenReturn(BigDecimal.valueOf(30.00));
        CartProduct cartProduct = mock(CartProduct.class);
        when(cartProduct.getProduct()).thenReturn(product);
        when(cartProduct.getQuantity()).thenReturn(2L);

        Promotion promotion = mock(Promotion.class);
        when(promotion.getRequiredQuantity()).thenReturn(3L);
        when(promotion.getFreeQuantity()).thenReturn(1L);
        BigDecimal result = new BuyGetFreeStrategy().applyPromotion(cartProduct, promotion);
        BigDecimal expectedTotal = BigDecimal.valueOf(30.00).multiply(BigDecimal.valueOf(2))
                .setScale(2, RoundingMode.HALF_EVEN);

        assertEquals(expectedTotal, result);
    }
}
