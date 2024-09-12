package com.test.qikserve.qikserve.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.Set;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductCartRecord(@NotNull UUID productId, String name, BigDecimal price, Long quantity, BigDecimal grossAmount,
                                Set<PromotionRecord> promotions) {
}
