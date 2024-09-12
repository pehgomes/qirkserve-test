package com.test.qikserve.qikserve.application.dto;

import com.test.qikserve.qikserve.domain.types.PromotionSort;
import com.test.qikserve.qikserve.domain.types.PromotionType;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class PromotionFilter {

    private PromotionType type;
    private String externalId;

    private int page = 0;
    private int size = 10;
    private Sort.Direction sort = Sort.Direction.ASC;
    private PromotionSort sortBy = PromotionSort.TYPE;
}
