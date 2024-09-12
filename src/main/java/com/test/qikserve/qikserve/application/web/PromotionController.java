package com.test.qikserve.qikserve.application.web;

import com.test.qikserve.qikserve.application.dto.PromotionFilter;
import com.test.qikserve.qikserve.application.dto.PromotionRecord;
import com.test.qikserve.qikserve.application.service.PromotionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/api/v1/promotions")
@RequiredArgsConstructor
@Tag(name = "Promotions", description = "Promotions operations.")
public class PromotionController {

    private final PromotionService promotionService;

    @GetMapping
    public Page<PromotionRecord> getPromotions(PromotionFilter filter) {
        return promotionService.getPromotions(filter);
    }

    @GetMapping("/{promotionId}")
    public PromotionRecord getById(@PathVariable UUID promotionId) {
        return promotionService.getById(promotionId);
    }

    @PostMapping
    public ResponseEntity<Void> createPromotion(@RequestBody PromotionRecord promotionRecord) {
        var promotion = promotionService.createPromotion(promotionRecord);

        return ResponseEntity.created(
                        fromCurrentRequest()
                                .path("/{id}")
                                .buildAndExpand(promotion.id())
                                .toUri())
                .build();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping("/{promotionId}")
    public void associateToProduct(@PathVariable UUID promotionId, @RequestBody PromotionRecord promotionRecord) {
        promotionService.updatePromotion(promotionId, promotionRecord);
    }
}
