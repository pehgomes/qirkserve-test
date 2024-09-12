package com.test.qikserve.qikserve.infra.specification;

import com.test.qikserve.qikserve.application.dto.PromotionFilter;
import com.test.qikserve.qikserve.domain.model.Promotion;
import com.test.qikserve.qikserve.domain.types.PromotionType;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;

public class PromotionSpecification {

    public static Specification<Promotion> filter(PromotionFilter filter) {
        return Specification.where(filterByType(filter.getType()))
                .and(filterByExternalId(filter.getExternalId()));

    }

    public static Specification<Promotion> filterByType(PromotionType promotionType) {
        return (root, query, builder) ->
                Optional.ofNullable(promotionType)
                        .map(
                                type ->
                                        builder.equal(
                                                root.get("promotionType"),
                                                type))
                        .orElse(null);
    }

    public static Specification<Promotion> filterByExternalId(String externalId) {
        return (root, query, builder) ->
                Optional.ofNullable(externalId)
                        .map(
                                externalIdValue ->
                                        builder.like(
                                                builder.lower(root.get("externalId")),
                                                externalIdValue.toLowerCase()))
                        .orElse(null);
    }
}
