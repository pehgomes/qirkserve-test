package com.test.qikserve.qikserve.infra.jpa;

import com.test.qikserve.qikserve.domain.model.CartProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartProductJpaRepository extends JpaRepository<CartProduct, UUID> {

    @Query("""
            SELECT cp FROM CartProduct cp
            WHERE cp.cart.id =:cartId AND cp.product.id =:productId
            """)
    Optional<CartProduct> findByCartIdAndProductId(UUID cartId, UUID productId);
}
