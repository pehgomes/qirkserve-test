package com.test.qikserve.qikserve.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.test.qikserve.qikserve.domain.types.PromotionType;

import java.math.BigDecimal;

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PromotionRecord(String id, PromotionType type, Long requiredQty,
                              Long freeQty, BigDecimal price, BigDecimal amount) {
}
