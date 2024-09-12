package com.test.qikserve.qikserve.domain.mapper;

import com.test.qikserve.qikserve.application.dto.CartRecord;
import com.test.qikserve.qikserve.application.dto.ProductCartRecord;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.domain.model.Cart;
import com.test.qikserve.qikserve.domain.model.CartProduct;
import com.test.qikserve.qikserve.domain.model.Promotion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CartMapper {

    @Mapping(target = "items", source = "products")
    CartRecord toRecord(Cart cart);

    @Mapping(target = "grossAmount", source = "amount")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "name", source = "product.name")
    @Mapping(target = "price", source = "product.price")
    @Mapping(target = "promotions", source = "product.promotions")
    ProductCartRecord toCartProductRecord(CartProduct cartProduct);

    @Mapping(target = "type", source = "promotionType")
    @Mapping(target = "requiredQty", source = "requiredQuantity")
    @Mapping(target = "freeQty", source = "freeQuantity")
    PromotionRecord toPromotionRecord(Promotion promotion);

}
