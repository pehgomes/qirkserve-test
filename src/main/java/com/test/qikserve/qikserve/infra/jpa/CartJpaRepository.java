package com.test.qikserve.qikserve.infra.jpa;

import com.test.qikserve.qikserve.domain.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartJpaRepository extends JpaRepository<Cart, UUID> {

    @Query("""
            SELECT c FROM Cart c
            LEFT JOIN FETCH c.products cp
            LEFT JOIN FETCH cp.product pd
            LEFT JOIN FETCH pd.promotions pm
            WHERE c.id =:id
            """)
    Optional<Cart> findById(UUID id);
}
