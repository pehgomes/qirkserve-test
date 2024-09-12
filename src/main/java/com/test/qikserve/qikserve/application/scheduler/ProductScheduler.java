package com.test.qikserve.qikserve.application.scheduler;

import com.test.qikserve.qikserve.application.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductScheduler {

    private final ProductService productService;

    @Scheduled(fixedRate = 1 * 60 * 1000)
    public void syncProducts() {
        log.info("Starting synchronization of products and promotions.");
        productService.syncProducts();
        productService.syncPromotions();
        log.info("Ending.");
    }
}
