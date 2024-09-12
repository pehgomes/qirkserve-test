package com.test.qikserve.qikserve.domain.types;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductSort {

    NAME("name"),
    EXTERNAL_ID("externalId");

    private final String description;

}
