package com.test.qikserve.qikserve.infra.client;

import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.domain.client.ProductClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductClientAdapter implements ProductClient {

    private final ProductsFeignClient productsFeignClient;

    @Override
    public List<ProductRecord> getProducts() {
        return productsFeignClient.getProducts();
    }

    @Override
    public ProductRecord getProductById(String id) {
        return productsFeignClient.getProductById(id);
    }
}
