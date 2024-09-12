package com.test.qikserve.qikserve.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.math.BigDecimal;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ProductRecord(String id, String name, BigDecimal price,
                            List<PromotionRecord> promotions) {

}