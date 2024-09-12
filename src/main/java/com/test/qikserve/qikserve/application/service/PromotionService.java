package com.test.qikserve.qikserve.application.service;

import com.test.qikserve.qikserve.application.dto.PromotionFilter;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.domain.types.PromotionType;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface PromotionService {

    PromotionRecord createPromotion(PromotionRecord record);

    PromotionRecord getById(UUID id);

    Page<PromotionRecord> getPromotions(PromotionFilter filter);

    void updatePromotion(UUID promotionId, PromotionRecord record);
}
