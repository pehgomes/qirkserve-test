package com.test.qikserve.qikserve.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record CartRecord(UUID id, @Valid List<ProductCartRecord> items, BigDecimal total, BigDecimal totalSaving) { }
