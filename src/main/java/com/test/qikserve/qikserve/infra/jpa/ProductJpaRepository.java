package com.test.qikserve.qikserve.infra.jpa;

import com.test.qikserve.qikserve.domain.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

    boolean existsByExternalId(String externalId);

    @Query("""
            SELECT prod FROM Product prod
            LEFT JOIN FETCH prod.promotions promo
            WHERE prod.externalId =:externalId
            """)
    Optional<Product> findByExternalId(String externalId);

    @Query("""
            SELECT prod FROM Product prod
            LEFT JOIN FETCH prod.promotions promo
            """)
    List<Product> findAllJoinPromotions();
}
