package com.test.qikserve.qikserve.domain.service;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.application.service.ProductService;
import com.test.qikserve.qikserve.domain.client.ProductClient;
import com.test.qikserve.qikserve.domain.mapper.ProductMapper;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.model.Promotion;
import com.test.qikserve.qikserve.domain.repository.ProductRepository;
import com.test.qikserve.qikserve.infra.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductClient productClient;
    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    @Override
    public Page<ProductRecord> getProducts(ProductFilter filter) {
        return productRepository.findProductByFilter(filter).map(productMapper::toRecord);
    }

    @Override
    public ProductRecord getProductById(UUID productId) {
        return productRepository.findProductById(productId)
                .map(productMapper::toRecord)
                .orElseThrow(() -> {
                    log.error("Product not found. {}", productId);
                    return new NotFoundException("Product not found. %s", productId);
                });
    }

    @Override
    public void syncProducts() {
        var productsRecord = productClient.getProducts();
        var products = productsRecord.stream().map(productMapper::toEntity)
                .filter(product -> !productRepository.existsByExternalId(product.getExternalId())).toList();
        productRepository.save(products);
    }

    @Override
    public void syncPromotions() {
        var products = productRepository.findAllJoinPromotions();
        products.forEach(product -> {
            var productRecord = productClient.getProductById(product.getExternalId());

            var newPromos = productRecord.promotions().stream()
                    .map(productMapper::toPromotionEntity)
                    .toList();

            Set<Promotion> filteredPromos = newPromos.stream()
                    .filter(newPromo -> product.getPromotions().stream()
                            .noneMatch(existingPromo -> existingPromo.getExternalId().equals(newPromo.getExternalId())))
                    .collect(Collectors.toSet());

            product.getPromotions().addAll(filteredPromos);

        });

        productRepository.save(products);
    }

    @Override
    public void update(UUID productId, ProductRecord record) {
        var product = productRepository.findProductById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found. {}", productId);
                    return new NotFoundException("Product not found. %s", productId);
                });

        productMapper.update(product, record);
        productRepository.save(product);
    }

    @Override
    public ProductRecord create(ProductRecord record) {
        Product product = productRepository.save(productMapper.toEntity(record));
        return productMapper.toRecord(product);
    }

}
