package com.test.qikserve.qikserve.infra.adapter;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.repository.CartProductRepository;
import com.test.qikserve.qikserve.infra.jpa.CartProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CartProductRepositoryAdapter implements CartProductRepository {

    private final CartProductJpaRepository cartProductJpaRepository;

    @Override
    public void delete(CartProduct cartProduct) {
        cartProductJpaRepository.delete(cartProduct);
    }

    @Override
    public CartProduct save(CartProduct cartProduct) {
        return cartProductJpaRepository.save(cartProduct);
    }

    @Override
    public Optional<CartProduct> findByIds(UUID cartId, UUID productId) {
        return cartProductJpaRepository.findByCartIdAndProductId(cartId, productId);
    }
}
