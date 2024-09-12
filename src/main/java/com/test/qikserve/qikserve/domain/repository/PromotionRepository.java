package com.test.qikserve.qikserve.domain.repository;

import com.test.qikserve.qikserve.application.dto.PromotionFilter;
import com.test.qikserve.qikserve.domain.model.Promotion;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface PromotionRepository {

    Promotion save(Promotion promotion);

    Optional<Promotion> findById(UUID id);

    Page<Promotion> findAllByType(PromotionFilter filter);
}
