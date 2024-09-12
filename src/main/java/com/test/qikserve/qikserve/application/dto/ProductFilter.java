package com.test.qikserve.qikserve.application.dto;

import com.test.qikserve.qikserve.domain.types.ProductSort;
import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class ProductFilter {

    private String name;
    private String externalId;

    private int page = 0;
    private int size = 10;
    private Sort.Direction sort = Sort.Direction.ASC;
    private ProductSort sortBy = ProductSort.NAME;


}
