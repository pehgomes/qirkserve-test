package com.test.qikserve.qikserve.application.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record QuantityRecord(Long quantity) {
}
