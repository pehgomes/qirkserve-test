package com.test.qikserve.qikserve.infra.adapter;

import com.test.qikserve.qikserve.application.dto.PromotionFilter;
import com.test.qikserve.qikserve.domain.model.Promotion;
import com.test.qikserve.qikserve.domain.repository.PromotionRepository;
import com.test.qikserve.qikserve.infra.jpa.PromotionJpaRepository;
import com.test.qikserve.qikserve.infra.specification.ProductSpecification;
import com.test.qikserve.qikserve.infra.specification.PromotionSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PromotionRepositoryAdapter implements PromotionRepository {

    private final PromotionJpaRepository promotionJpaRepository;
    @Override
    public Promotion save(Promotion promotion) {
        return promotionJpaRepository.save(promotion);
    }

    @Override
    public Optional<Promotion> findById(UUID id) {
        return promotionJpaRepository.findById(id);
    }

    @Override
    public Page<Promotion> findAllByType(PromotionFilter filter) {
        final var pageable =
                PageRequest.of(
                        filter.getPage(),
                        filter.getSize(),
                        filter.getSort(), filter.getSortBy().getDescription());

        final var spec = PromotionSpecification.filter(filter);

        return promotionJpaRepository.findAll(spec, pageable);
    }
}
