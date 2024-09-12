package com.test.qikserve.qikserve.infra.jpa;

import com.test.qikserve.qikserve.domain.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PromotionJpaRepository extends JpaRepository<Promotion, UUID>, JpaSpecificationExecutor<Promotion> {
}
