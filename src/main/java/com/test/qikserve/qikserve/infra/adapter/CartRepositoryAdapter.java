package com.test.qikserve.qikserve.infra.adapter;

import com.test.qikserve.qikserve.domain.model.Cart;
import com.test.qikserve.qikserve.domain.repository.CartRepository;
import com.test.qikserve.qikserve.infra.jpa.CartJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartRepositoryAdapter implements CartRepository {

    private final CartJpaRepository cartJpaRepository;

    @Override
    public Cart save(Cart cart) {
        return cartJpaRepository.save(cart);
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        return cartJpaRepository.findById(id);
    }
}
