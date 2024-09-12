package com.test.qikserve.qikserve.domain.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PromotionSort {

    TYPE("promotionType"),
    EXTERNAL_ID("externalId");

    private final String description;

}
