package com.test.qikserve.qikserve.domain.mapper;

import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.domain.model.Promotion;
import org.mapstruct.*;

@Mapper
public interface PromotionMapper {

    Promotion toEntity(PromotionRecord record);

    PromotionRecord toRecord(Promotion promotion);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(source = "type", target = "promotionType")
    @Mapping(source = "requiredQty", target = "requiredQuantity")
    @Mapping(source = "freeQty", target = "freeQuantity")
    void updatePromotion(PromotionRecord record, @MappingTarget Promotion promotion);
}
