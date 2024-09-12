package com.test.qikserve.qikserve.application.service;

import com.test.qikserve.qikserve.application.dto.CartRecord;

import java.util.UUID;

public interface CartProductService {

    CartRecord addProductInCart(CartRecord cart);

    CartRecord getCartById(UUID cartId);

    void addProductsInCart(UUID cartId, CartRecord cartRecord);

    void removeProductFromCart(UUID cartId, UUID productId);
}
