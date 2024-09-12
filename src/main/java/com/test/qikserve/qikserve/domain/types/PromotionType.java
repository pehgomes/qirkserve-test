package com.test.qikserve.qikserve.domain.types;

import com.test.qikserve.qikserve.domain.service.strategy.BuyGetFreeStrategy;
import com.test.qikserve.qikserve.domain.service.strategy.FlatPercentStrategy;
import com.test.qikserve.qikserve.domain.service.strategy.PromotionStrategy;
import com.test.qikserve.qikserve.domain.service.strategy.QtyBasePriceStrategy;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PromotionType {

    BUY_X_GET_Y_FREE(new BuyGetFreeStrategy()),
    QTY_BASED_PRICE_OVERRIDE(new QtyBasePriceStrategy()),
    FLAT_PERCENT(new FlatPercentStrategy());

    private final PromotionStrategy promotionStrategy;

}
