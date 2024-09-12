package com.test.qikserve.qikserve.domain.repository;

import com.test.qikserve.qikserve.domain.model.CartProduct;

import java.util.Optional;
import java.util.UUID;

public interface CartProductRepository {

    void delete(CartProduct cartProduct);
    CartProduct save(CartProduct cartProduct);

    Optional<CartProduct> findByIds(UUID cartId, UUID productId);
}
