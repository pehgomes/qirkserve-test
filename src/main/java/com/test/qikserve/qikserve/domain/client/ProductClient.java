package com.test.qikserve.qikserve.domain.client;

import com.test.qikserve.qikserve.application.dto.ProductRecord;

import java.util.List;

public interface ProductClient {

    List<ProductRecord> getProducts();

    ProductRecord getProductById(String id);
}
