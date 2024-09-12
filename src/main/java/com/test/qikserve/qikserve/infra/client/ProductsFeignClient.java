package com.test.qikserve.qikserve.infra.client;

import com.test.qikserve.qikserve.application.dto.ProductRecord;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "productsClient", url = "${api.products.base-url}")
public interface ProductsFeignClient {

    @GetMapping("/products")
    List<ProductRecord> getProducts();

    @GetMapping("/products/{productId}")
    ProductRecord getProductById(@PathVariable String productId);
}
