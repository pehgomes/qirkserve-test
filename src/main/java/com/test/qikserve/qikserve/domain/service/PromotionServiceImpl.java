package com.test.qikserve.qikserve.domain.service;

import com.test.qikserve.qikserve.application.dto.PromotionFilter;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.application.service.PromotionService;
import com.test.qikserve.qikserve.domain.mapper.PromotionMapper;
import com.test.qikserve.qikserve.domain.model.Promotion;
import com.test.qikserve.qikserve.domain.repository.PromotionRepository;
import com.test.qikserve.qikserve.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromotionServiceImpl implements PromotionService {

    private final PromotionRepository promotionRepository;
    private final PromotionMapper mapper = Mappers.getMapper(PromotionMapper.class);

    @Override
    public PromotionRecord createPromotion(PromotionRecord record) {
        Promotion newPromotion = promotionRepository.save(mapper.toEntity(record));
        return mapper.toRecord(newPromotion);
    }

    @Override
    public PromotionRecord getById(UUID id) {
        return promotionRepository.findById(id).map(mapper::toRecord)
                .orElseThrow(() -> {
                    log.error("Promotion not found. {}", id);
                    return new NotFoundException("Promotion not found. %s", id);
                });
    }

    @Override
    public Page<PromotionRecord> getPromotions(PromotionFilter filter) {
        return promotionRepository.findAllByType(filter).map(mapper::toRecord);
    }


    @Override
    public void updatePromotion(UUID promotionId, PromotionRecord record) {
        var promotion = promotionRepository.findById(promotionId).orElseThrow(() -> {
            log.info("Promotion not found. {}", promotionId);
            return new NotFoundException("Promotion not found. %s", promotionId);
        });

        mapper.updatePromotion(record, promotion);
        promotionRepository.save(promotion);
    }
}
