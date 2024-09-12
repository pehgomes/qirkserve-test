package com.test.qikserve.qikserve.infra.adapter;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.repository.ProductRepository;
import com.test.qikserve.qikserve.infra.jpa.ProductJpaRepository;
import com.test.qikserve.qikserve.infra.specification.ProductSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ProductRepositoryAdapter implements ProductRepository {

    private final ProductJpaRepository jpaRepository;

    @Override
    public Product save(Product product) {
        return jpaRepository.save(product);
    }

    @Override
    public void save(List<Product> products) {
        jpaRepository.saveAll(products);
    }

    @Override
    public List<Product> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public List<Product> findAllJoinPromotions() {
        return jpaRepository.findAllJoinPromotions();
    }

    @Override
    public boolean existsByExternalId(String externalId) {
        return jpaRepository.existsByExternalId(externalId);
    }

    @Override
    public Page<Product> findProductByFilter(ProductFilter filter) {

        final var pageable =
                PageRequest.of(
                        filter.getPage(),
                        filter.getSize(),
                        filter.getSort(), filter.getSortBy().getDescription());

        final var spec = ProductSpecification.filter(filter);

        return jpaRepository.findAll(spec, pageable);
    }

    @Override
    public Optional<Product> findProductById(UUID id) {
        return jpaRepository.findById(id);
    }


}
