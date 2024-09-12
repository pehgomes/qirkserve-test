package com.test.qikserve.qikserve.infra.specification;

import com.test.qikserve.qikserve.application.dto.ProductFilter;
import com.test.qikserve.qikserve.domain.model.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductSpecification {


    public static Specification<Product> filter(ProductFilter filter) {
        return Specification.where(filterByProductName(filter.getName()))
                .and(filterByExternalId(filter.getExternalId()));
    }

    public static Specification<Product> filterByProductName(String name) {
        return (root, query, builder) ->
                Optional.ofNullable(name)
                        .map(
                                descriptionName ->
                                        builder.like(
                                                builder.lower(root.get("name")),
                                                "%" + descriptionName.toLowerCase() + "%"))
                        .orElse(null);
    }

    public static Specification<Product> filterByExternalId(String id) {
        return (root, query, builder) ->
                Optional.ofNullable(id)
                        .map(
                                externalId ->
                                        builder.equal(root.get("externalId"), externalId))
                        .orElse(null);
    }


}
