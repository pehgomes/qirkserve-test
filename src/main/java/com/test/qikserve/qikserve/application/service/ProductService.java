package com.test.qikserve.qikserve.application.service;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.application.dto.ProductRecord;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface ProductService {
    Page<ProductRecord> getProducts(ProductFilter filter);

    ProductRecord getProductById(UUID productId);

    void syncProducts();

    void syncPromotions();

    void update(UUID productId, ProductRecord record);

    ProductRecord create(ProductRecord record);
}
