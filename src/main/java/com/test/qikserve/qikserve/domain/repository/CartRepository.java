package com.test.qikserve.qikserve.domain.repository;

import com.test.qikserve.qikserve.domain.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository {

    Cart save(Cart cart);

    Optional<Cart> findById(UUID id);
}
