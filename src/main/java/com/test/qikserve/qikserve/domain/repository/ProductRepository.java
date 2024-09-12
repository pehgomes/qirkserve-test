package com.test.qikserve.qikserve.domain.repository;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.domain.model.Product;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {

    Product save(Product product);

    void save(List<Product> products);

    List<Product> findAll();

    List<Product> findAllJoinPromotions();

    boolean existsByExternalId(String externalId);

    Page<Product> findProductByFilter(ProductFilter filter);

    Optional<Product> findProductById(UUID id);
}
