package com.test.qikserve.qikserve.domain.mapper;

import com.test.qikserve.qikserve.application.dto.ProductRecord;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.domain.model.Product;
import com.test.qikserve.qikserve.domain.model.Promotion;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Mapper
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "id", target = "externalId")
    @Mapping(source = "price", target = "price", qualifiedByName = "toMonetary")
    Product toEntity(ProductRecord productRecord);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Product product, ProductRecord record);

    @Mapping(target = "type", source = "promotionType")
    @Mapping(target = "requiredQty", source = "requiredQuantity")
    @Mapping(target = "freeQty", source = "freeQuantity")
    PromotionRecord toPromotionRecord(Promotion promotion);

    ProductRecord toRecord(Product product);

    default Promotion toPromotionEntity(PromotionRecord promoRecord) {
        return new Promotion(promoRecord.id(), promoRecord.type(), promoRecord.requiredQty(),
                toMonetary(promoRecord.price()), promoRecord.amount(), promoRecord.freeQty());
    }

    @Named("toMonetary")
    default BigDecimal toMonetary(BigDecimal value) {
        if (value == null) return null;
        return BigDecimal.valueOf(value.doubleValue() / 100)
                .setScale(2, RoundingMode.HALF_EVEN);
    }
}
